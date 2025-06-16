package cn.valuetodays.api2.module.fortune.task;

import cn.valuetodays.api2.module.fortune.service.StockNewAccountService;
import cn.valuetodays.api2.web.common.IVtNatsClient;
import cn.valuetodays.quarkus.commons.base.RunAsync;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@ApplicationScoped
public class StockNewAccountTask extends RunAsync {
    @Inject
    IVtNatsClient vtNatsClient;

    @Inject
    StockNewAccountService stockNewAccountService;

    //    @Scheduled(cron = "1 15 1 * * SUN") // 每周日01:15:01
    @Scheduled(cron = "1 15 1 * * ?") // 每日01:15:01
//    @DistributeLock(id = "schedulePost", milliSeconds = TimeConstants.T3m)
    public void schedule() {
        super.executeAsync(() -> {
            vtNatsClient.publishApplicationMessage(StockNewAccountTask.class.getSimpleName() + " begin");
            stockNewAccountService.refresh();
            vtNatsClient.publishApplicationMessage(StockNewAccountTask.class.getSimpleName() + " end");
        });
    }
}
