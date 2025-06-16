package cn.valuetodays.api2.basic.task;

import cn.valuetodays.api2.basic.service.RsaKeyPairService;
import cn.valuetodays.api2.web.common.IVtNatsClient;
import cn.valuetodays.quarkus.commons.base.RunAsync;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.RandomUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-09
 */
@ApplicationScoped
public class RsaKeyPairTask extends RunAsync {

    @Inject
    RsaKeyPairService rsaKeyPairService;
    @Inject
    IVtNatsClient vtNatsClient;

    @Scheduled(cron = "0 5 0 * * ?") // 每天00:05
    public void deleteOldKeyPairs() {
        super.executeAsync(() -> {
            vtNatsClient.publishApplicationMessage(RsaKeyPairTask.class.getSimpleName() + "#deleteOldKeyPairs() begin");
            rsaKeyPairService.deleteOldKeyPairs();
            vtNatsClient.publishApplicationMessage(RsaKeyPairTask.class.getSimpleName() + "#deleteOldKeyPairs() end");
        });
    }

    @Scheduled(cron = "0 15 0 * * ?") // 每天00:05
//    @Scheduled(every = "30s")
    public void renewKeyPairs() {
        super.executeAsync(() -> {
            vtNatsClient.publishApplicationMessage(RsaKeyPairTask.class.getSimpleName() + "#renewKeyPairs() begin");
            rsaKeyPairService.renewKeyPairs(RandomUtils.secure().randomInt(2, 5));
            vtNatsClient.publishApplicationMessage(RsaKeyPairTask.class.getSimpleName() + "#renewKeyPairs() end");
        });
    }
}
