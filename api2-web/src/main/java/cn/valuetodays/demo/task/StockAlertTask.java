package cn.valuetodays.demo.task;

import cn.valuetodays.api2.persist.enums.StockAlertEnums;
import cn.valuetodays.demo.service.StockAlertService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-16
 */
@ApplicationScoped
@Slf4j
public class StockAlertTask {
    @Inject
    private StockAlertService stockAlertService;

    /**
     * 要比 cn.valuetodays.module.fortune.task.QuoteDailyStatTask#scheduleRefreshAll() 晚
     */
    @Scheduled(cron = "10 00 15 * * ?") // 每天15:00:10
//    @DistributeLock(id = "scheduleRefreshAfterMarketClose", milliSeconds = TimeConstants.T3m)
    public void scheduleRefreshAfterMarketClose() {
        log.info("begin to refresh scheduleRefreshAfterMarketClose");
        stockAlertService.scheduleAlert(StockAlertEnums.ScheduleType.CLOSE);
        log.info("end to refresh scheduleRefreshAfterMarketClose");
    }

    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟
//    @DistributeLock(id = "scheduleRefresh10Min", milliSeconds = TimeConstants.T3m)
    public void scheduleRefresh10Min() {
        log.info("begin to refresh scheduleRefresh10Min");
        stockAlertService.scheduleAlert(StockAlertEnums.ScheduleType.EVERY_10_MIN);
        log.info("end to refresh scheduleRefresh10Min");
    }

    @Scheduled(cron = "0 0/20 * * * ?") // 每20分钟
//    @DistributeLock(id = "scheduleRefresh20Min", milliSeconds = TimeConstants.T3m)
    public void scheduleRefresh20Min() {
        log.info("begin to refresh scheduleRefresh20Min");
        stockAlertService.scheduleAlert(StockAlertEnums.ScheduleType.EVERY_20_MIN);
        log.info("end to refresh scheduleRefresh20Min");
    }
}
