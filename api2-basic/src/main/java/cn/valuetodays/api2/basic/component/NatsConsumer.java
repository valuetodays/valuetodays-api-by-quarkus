package cn.valuetodays.api2.basic.component;

import cn.valuetodays.api2.basic.NatsConstants;
import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
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
import java.util.function.Consumer;

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


    void onStartup(@Observes @Priority(PriorityConstant.NATS_CONSUMER_ORDER) StartupEvent unused) {
        int tryTimes = 1;
        while (tryTimes < 30 && (vtNatsClient.isConnected())) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {
            }
            tryTimes++;
        }

        Consumer<Dispatcher> consumer = getDispatcherConsumer();
        vtNatsClient.subscribe(consumer);
    }

    private Consumer<Dispatcher> getDispatcherConsumer() {
        MessageHandler messageHandlerForMsg = msg -> {
            String subject = msg.getSubject();
            String msgText = new String(msg.getData(), StandardCharsets.UTF_8);
            LOGGER.info("Received message {}, on subject {}", msgText, subject);
            try {
                notifyService.notifyApplicationMsg(msgText);
            } catch (Exception e) {
                log.error("error,", e);
            }
        };
        MessageHandler messageHandlerForEx = msg -> {
            String subject = msg.getSubject();
            String msgText = new String(msg.getData(), StandardCharsets.UTF_8);
            LOGGER.info("Received message {}, on subject {}", msgText, subject);
            notifyService.notifyApplicationException(vtNatsClient.applicationName, msgText);
        };
        Consumer<Dispatcher> consumer = (dispatcher) -> {
            dispatcher.subscribe(
                NatsConstants.Topic.TOPIC_APPLICATIONMSG,
                messageHandlerForMsg
            );
            log.info("subscribe topic: {}", NatsConstants.Topic.TOPIC_APPLICATIONMSG);

            dispatcher.subscribe(
                NatsConstants.Topic.TOPIC_APPLICATIONEX,
                messageHandlerForEx
            );
            log.info("subscribe topic: {}", NatsConstants.Topic.TOPIC_APPLICATIONEX);
        };
        log.info("consumer={}", consumer);
        return consumer;
    }
}
