package cn.valuetodays.api2.web.task;

import cn.valuetodays.api2.basic.component.VtNatsClient;
import cn.valuetodays.api2.module.fortune.service.IndustryDailyStatService;
import cn.valuetodays.quarkus.commons.base.RunAsync;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@ApplicationScoped
@Slf4j
public class IndustryDailyStatTask extends RunAsync {
    @Inject
    IndustryDailyStatService industryDailyStatService;
    @Inject
    VtNatsClient vtNatsClient;

    @Blocking
    @Scheduled(cron = "10 0 18 ? * MON-FRI") // 每工作日18:00:10
    public void scheduleRefresh() {
        super.executeAsync(() -> {
            vtNatsClient.publishApplicationMessage("begin to refresh scheduleRefresh");
            log.info("begin to refresh scheduleRefresh");
            industryDailyStatService.refresh();
            log.info("end to refresh scheduleRefresh");
            vtNatsClient.publishApplicationMessage("end to refresh scheduleRefresh");
        });
    }

    @Blocking
    @Scheduled(cron = "10 0 23 ? * MON-FRI") // 每工作日23:00:10
    public void scheduleRefreshTwice() {
        scheduleRefresh();
    }
}
