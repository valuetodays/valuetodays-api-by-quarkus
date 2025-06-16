package cn.valuetodays.api2.module.fortune.task;

import cn.valuetodays.api2.module.fortune.service.IndustryDailyStatService;
import cn.valuetodays.api2.web.common.IVtNatsClient;
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
    IVtNatsClient vtNatsClient;

    @Blocking
    @Scheduled(cron = "10 0 18 ? * MON-FRI") // 每工作日18:00:10
    public void scheduleRefresh() {
        super.executeAsync(() -> {
            vtNatsClient.publishApplicationMessage(IndustryDailyStatTask.class.getSimpleName() + " begin");
            log.info("begin to refresh scheduleRefresh");
            industryDailyStatService.refresh();
            log.info("end to refresh scheduleRefresh");
            vtNatsClient.publishApplicationMessage(IndustryDailyStatTask.class.getSimpleName() + " end");
        });
    }

    @Blocking
    @Scheduled(cron = "10 0 23 ? * MON-FRI") // 每工作日23:00:10
    public void scheduleRefreshTwice() {
        scheduleRefresh();
    }
}
