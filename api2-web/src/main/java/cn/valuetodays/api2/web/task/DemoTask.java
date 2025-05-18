package cn.valuetodays.api2.web.task;

import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-07
 */
@ApplicationScoped
public class DemoTask {
    private final AtomicInteger counter = new AtomicInteger();

    @Scheduled(cron = "0/58 * * * * ?")
    void cronJob() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.intValue();
    }
}
