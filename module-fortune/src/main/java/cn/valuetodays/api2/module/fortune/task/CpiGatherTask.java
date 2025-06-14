package cn.valuetodays.api2.module.fortune.task;

import cn.valuetodays.api2.module.fortune.service.CpiSpiderModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-01-13
 */
@ApplicationScoped
@Slf4j
public class CpiGatherTask {
    @Inject
    ManagedExecutor managedExecutor;
    @Inject
    CpiSpiderModule cpiSpiderModule;

    //    @Scheduled(cron = "1 30 5 * * ?") // 每天05:30:01
    // 本来应该每月跑一次，但是怕程序停止了，就每周一次吧
    @Scheduled(cron = "1 30 5 ? * THU") // 每周四05:30:01
//    @DistributeLock(id = "scheduleRefresh", milliSeconds = TimeConstants.T3m)
    public void scheduleRefresh() {
        managedExecutor.execute(() -> {
            log.info("begin to refresh etfInfo");
            cpiSpiderModule.refreshCpiData();
            log.info("end to refresh etfInfo");
        });
    }
}
