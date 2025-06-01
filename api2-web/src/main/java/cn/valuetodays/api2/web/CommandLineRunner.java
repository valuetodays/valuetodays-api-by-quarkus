package cn.valuetodays.api2.web;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@Singleton
@Startup
@Slf4j
public class CommandLineRunner {

    @ConfigProperty(name = "quarkus.redis.database")
    int redisDatabase;

    @PostConstruct
    public void show() {
        log.info("redisDatabase={}", redisDatabase);
    }

}
