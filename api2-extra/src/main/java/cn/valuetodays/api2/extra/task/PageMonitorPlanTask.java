package cn.valuetodays.api2.extra.task;

import cn.valuetodays.api2.extra.service.PageMonitorPlanServiceImpl;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-02-08
 */
//@Component
//@Profile("!dev")
public class PageMonitorPlanTask {
    @Inject
    PageMonitorPlanServiceImpl pageMonitorPlanService;

    @Scheduled(delay = 2, every = "15m")
//    @DistributeLock(id="schedule", milliSeconds = TimeConstants.T58s)
    public void schedule() {
        pageMonitorPlanService.doSchedule();
    }
}
