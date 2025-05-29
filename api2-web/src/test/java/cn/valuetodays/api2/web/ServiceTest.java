package cn.valuetodays.api2.web;

import cn.valuetodays.api2.web.component.CookieCacheComponent;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-20
 */
@QuarkusTest
@Slf4j
public class ServiceTest {
    @Inject
    private CookieCacheComponent cookieCacheComponent;

    @Test
    public void ss() {
        log.info("{}", cookieCacheComponent);
    }
}
