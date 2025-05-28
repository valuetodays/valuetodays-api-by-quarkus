package cn.valuetodays.api2.basic.component;

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

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-28
 */
@ApplicationScoped
@Priority(100)
@Slf4j
public class NatsConsumer {
    public static final Logger LOGGER = LoggerFactory.getLogger(NatsConsumer.class);

    @Inject
    VtNatsClient vtNatsClient;

    // 设置优先级较高的@StartupEvent方法，值越低，优先级越高
    @Priority(100)
    void onStartup(@Observes StartupEvent unused) {
        Dispatcher dispatcher = vtNatsClient.connection.createDispatcher();

        MessageHandler messageHandler = msg -> {
            String subject = msg.getSubject();
            String msgText = new String(msg.getData(), StandardCharsets.UTF_8);
            LOGGER.info("Received message {}, on subject {}", msgText, subject);
        };
        dispatcher.subscribe("abc",
            messageHandler
        );
    }
}
