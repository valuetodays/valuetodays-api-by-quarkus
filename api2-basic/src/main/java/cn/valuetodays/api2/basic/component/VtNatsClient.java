package cn.valuetodays.api2.basic.component;

import cn.valuetodays.quarkus.commons.base.ProfileUtils;
import cn.vt.exception.AssertUtils;
import cn.vt.exception.CommonException;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.util.ExceptionUtil;
import jakarta.annotation.Priority;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-28
 */
@Singleton
@Slf4j
public class VtNatsClient {
    public static final String TOPIC_APPLICATION_MESSAGE = "applicationmsg";
    @Inject
    ProfileUtils profileUtils;
    private Connection connection;
    @ConfigProperty(name = "nats.server")
    String natsServer;
    @ConfigProperty(name = "nats.token")
    String natsToken;
    @ConfigProperty(name = "quarkus.application.name")
    String applicationName;

    // 设置优先级较高的@StartupEvent方法，值越高，越先运行

    void onStartup(@Observes @Priority(PriorityConstant.NATS_CLIENT_INIT_ORDER) StartupEvent unused) {
//        log.info("connecting to nats server with {}", natsToken);
        try {
            Options options = new Options.Builder()
                .server(natsServer)
                .token(natsToken.toCharArray())
                .build();

            connection = Nats.connect(options);
            log.info("connected to nats server");
        } catch (Exception e) {
            log.error("error when Nats.connect()", e);
            throw new CommonException("can not access nats", e);
        }
    }


    public void publish(String topic, String msg) {
        if (profileUtils.isProd()) {
            connection.publish(topic, msg.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void publishApplicationMessage(String msg) {
        this.publish(TOPIC_APPLICATION_MESSAGE, msg);
    }

    public void publishApplicationException(String msgPrefix, Throwable ex) {
        String exceptionStackTraceString = ExceptionUtil.generateStackTrace(ex);
        String first800 = StringUtils.substring(exceptionStackTraceString, 0, 800);
        this.publish(TOPIC_APPLICATION_MESSAGE, "[" + applicationName + "] " + msgPrefix + first800);
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

    public boolean isConnected() {
        return Objects.nonNull(connection);
    }

    public void subscribe(Consumer<Dispatcher> consumer) {
        AssertUtils.assertNotNull(connection, "can not get connection from nats server");
        log.info("connection is not null");
        Dispatcher dispatcher = connection.createDispatcher();
        consumer.accept(dispatcher);
    }
}
