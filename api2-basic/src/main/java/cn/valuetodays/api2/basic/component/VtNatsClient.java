package cn.valuetodays.api2.basic.component;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-28
 */
@Singleton
@Slf4j
public class VtNatsClient {
    public static final String TOPIC_APPLICATION_MESSAGE = "application_message";
    public Connection connection;
    @ConfigProperty(name = "nats.server")
    String natsServer;
    @ConfigProperty(name = "nats.token")
    String natsToken;

    // 设置优先级较高的@StartupEvent方法，值越低，优先级越高
    @Priority(1)
    void onStartup(@Observes StartupEvent unused) {
        log.info("connecting to nats server with {}", natsToken);
        try {
            // 连接到 NATS 服务器，这里使用默认的本地地址和端口，你可以根据实际情况修改
            Options options = new Options.Builder()
                .server(natsServer)
                .token(natsToken.toCharArray())
                .build();

            connection = Nats.connect(options);
            log.info("connected to nats server");
        } catch (Exception e) {
            log.error("error when Nats.connect()", e);
            throw new RuntimeException("can not access nats");
        }
    }


    public void publish(String topic, String msg) {
        connection.publish(topic, msg.getBytes(StandardCharsets.UTF_8));
    }

    public void publishApplicationMessage(String msg) {
        this.publish(TOPIC_APPLICATION_MESSAGE, msg);
    }

    void onShutdown(@Observes ShutdownEvent unused) {
        log.info("disconnect from nats server");
        if (Objects.isNull(connection)) {
            return;
        }
        try {
            connection.close();
            log.info("disconnected from nats server");
        } catch (Exception e) {
            log.error("error when connection.close()", e);
            throw new RuntimeException(e);
        }
    }
}
