package cn.valuetodays.api2.basic.task;

import cn.valuetodays.api2.basic.service.RsaKeyPairService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-09
 */
@ApplicationScoped
public class RsaKeyPairTask {

    @Inject
    RsaKeyPairService rsaKeyPairService;

    @Scheduled(cron = "0 5 0 * * ?") // 每天00:05
    public void deleteOldKeyPairs() {
        rsaKeyPairService.deleteOldKeyPairs();
    }

    @Scheduled(cron = "0 15 0 * * ?") // 每天00:05
//    @Scheduled(every = "30s")
    public void renewKeyPairs() {
        rsaKeyPairService.renewKeyPairs(3);
    }
}
