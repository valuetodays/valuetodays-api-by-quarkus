package cn.valuetodays.demo.task;

import cn.valuetodays.demo.service.StockNewAccountService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@ApplicationScoped
public class StockNewAccountTask {
    @Inject
    private StockNewAccountService stockNewAccountService;

    //    @Scheduled(cron = "1 15 1 * * SUN") // 每周日01:15:01
    @Scheduled(cron = "1 15 1 * * ?") // 每日01:15:01
//    @DistributeLock(id = "schedulePost", milliSeconds = TimeConstants.T3m)
    public void schedule() {
        stockNewAccountService.refresh();
    }
}
