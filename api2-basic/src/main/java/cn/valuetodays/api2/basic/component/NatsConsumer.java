package cn.valuetodays.api2.basic.component;

import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.vt.exception.AssertUtils;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.MessageHandler;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-28
 */
@ApplicationScoped
@Priority(PriorityConstant.NATS_CONSUMER_ORDER)
@Slf4j
public class NatsConsumer {
    public static final Logger LOGGER = LoggerFactory.getLogger(NatsConsumer.class);

    @Inject
    VtNatsClient vtNatsClient;
    @Inject
    NotifyServiceImpl notifyService;

    @Priority(PriorityConstant.NATS_CONSUMER_ORDER)
    void onStartup(@Observes StartupEvent unused) {
        Connection conn = null;
        int tryTimes = 1;
        while (tryTimes < 10 && (conn = vtNatsClient.connection) == null) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {
            }
            tryTimes++;
        }
        AssertUtils.assertNotNull(conn, "can not get conn from nats server");
        log.info("conn is not null");
        Dispatcher dispatcher = conn.createDispatcher();

        MessageHandler messageHandler = msg -> {
            String subject = msg.getSubject();
            String msgText = new String(msg.getData(), StandardCharsets.UTF_8);
            LOGGER.info("Received message {}, on subject {}", msgText, subject);
            try {
                notifyService.notifyApplicationMsg(msgText);
            } catch (Exception e) {
                log.error("error,", e);
            }
        };
        dispatcher.subscribe("applicationmsg",
            messageHandler
        );
        log.info("subscribe topic: {}", "applicationmsg");

        MessageHandler messageHandlerForEx = msg -> {
            String subject = msg.getSubject();
            String msgText = new String(msg.getData(), StandardCharsets.UTF_8);
            LOGGER.info("Received message {}, on subject {}", msgText, subject);
            notifyService.notifyApplicationException(vtNatsClient.applicationName, msgText);
        };
        dispatcher.subscribe("applicationex",
            messageHandlerForEx
        );
        log.info("subscribe topic: {}", "applicationex");
    }
}
