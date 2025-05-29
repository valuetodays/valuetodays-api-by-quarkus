package cn.valuetodays.api2.web;

import cn.valuetodays.api2.basic.component.VtNatsClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-29
 */
@QuarkusTest
public class NatsClientTest {
    @Inject
    VtNatsClient natsClient;

    @Test
    public void testNats() throws InterruptedException {
        natsClient.publishApplicationException("applicationex", new RuntimeException("fdsdfs"));
        natsClient.publishApplicationMessage("abc");
        TimeUnit.MINUTES.sleep(5);
    }
}
