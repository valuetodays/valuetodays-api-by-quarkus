package cn.valuetodays.api2.web.task;

import cn.valuetodays.api2.web.service.IndustryDailyStatService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@ApplicationScoped
@Slf4j
public class IndustryDailyStatTask {
    @Inject
    private IndustryDailyStatService industryDailyStatService;

    @Scheduled(cron = "10 00 18 ? * MON-FRI") // 每工作日18:00:10
    public void scheduleRefresh() {
        log.info("begin to refresh scheduleRefresh");
        industryDailyStatService.refresh();
        log.info("end to refresh scheduleRefresh");
    }

    @Scheduled(cron = "10 00 23 ? * MON-FRI") // 每工作日23:00:10
    public void scheduleRefreshTwice() {
        scheduleRefresh();
    }
}
