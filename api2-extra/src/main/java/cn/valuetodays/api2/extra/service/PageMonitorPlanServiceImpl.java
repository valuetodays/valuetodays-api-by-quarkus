package cn.valuetodays.api2.extra.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import cn.valuetodays.api2.extra.dao.PageMonitorPlanDAO;
import cn.valuetodays.api2.extra.enums.PageMonitorPlanType;
import cn.valuetodays.api2.extra.persist.PageMonitorLogPO;
import cn.valuetodays.api2.extra.persist.PageMonitorPlanPO;
import cn.valuetodays.api2.extra.service.plan.IPlan;
import cn.valuetodays.api2.extra.service.plan.PlanReq;
import cn.valuetodays.api2.extra.service.plan.PlanResp;
import cn.valuetodays.api2.web.common.CommonEnums;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.util.DateUtils;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 页面监控计划
 *
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@ApplicationScoped
@Slf4j
public class PageMonitorPlanServiceImpl
    extends BaseCrudService<Long, PageMonitorPlanPO, PageMonitorPlanDAO> {

    private static final CommonEnums.YNEnum statusY = CommonEnums.YNEnum.Y;

    @Inject
    PageMonitorLogServiceImpl pageMonitorLogService;

    public void doSchedule() {
        int pageIndex = 0;
        final int size = 30;
        while (true) {
//            PageRequest pr = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "id"));
            Page page = Page.of(pageIndex, size);
            List<PageMonitorPlanPO> contents = getRepository().findAllByEnableStatus(statusY, page);
//            List<PageMonitorPlanPO> contents = pagedData.getContent();
            if (CollectionUtils.isEmpty(contents)) {
                log.warn("no PageMonitorPlanPO contents in pageIndex #{}", pageIndex);
                break;
            }
            for (PageMonitorPlanPO content : contents) {
                processPlan(content);
            }
            pageIndex++;
        }
    }

    private void processPlan(PageMonitorPlanPO content) {
        if (Objects.isNull(content)) {
            return;
        }
        PageMonitorPlanType.Conf conf = content.getConf();
        if (Objects.isNull(conf)) {
            return;
        }
        PageMonitorPlanType.ScheduleRate scheduleRate = conf.getScheduleRate();
        if (Objects.isNull(scheduleRate)) {
            return;
        }

        LocalDateTime today = DateUtils.getToday();
        LocalDateTime lastScheduleTime = content.getLastScheduleTime();
        boolean isScheduled = Objects.nonNull(lastScheduleTime);
        if (PageMonitorPlanType.ScheduleRate.DAY == scheduleRate) {
            if (isScheduled) {
                // 若5分钟之内已保存过，就不再保存了
                if (Math.abs(DateUtils.intervalSeconds(today, lastScheduleTime)) < 300) {
                    /* log.info("seems another calcLog was saved within 61 seconds. "
                        + "no need to save again. segmentId:{}", segmentId); */
                    return;
                }

                // 若上次计算时间是今天前1天之前，就开始运行
                long minuteOffset = Math.abs(DateUtils.intervalMinutes(today, lastScheduleTime));
                if (minuteOffset > 60 * 24) {
                    doProcessPlan(content);
                } else {
                    // do nothing
                }
            } else {
                doProcessPlan(content);
            }
        } else if (PageMonitorPlanType.ScheduleRate.WEEK == scheduleRate) {
            if (isScheduled) {
                // 若5分钟之内已保存过，就不再保存了
                if (Math.abs(DateUtils.intervalSeconds(today, lastScheduleTime)) < 300) {
                    /* log.info("seems another calcLog was saved within 61 seconds. "
                        + "no need to save again. segmentId:{}", segmentId); */
                    return;
                }

                // 若上次计算时间是今天前7天之前，就开始运行
                long minuteOffset = Math.abs(DateUtils.intervalMinutes(today, lastScheduleTime));
                if (minuteOffset > 60 * 24 * 7) {
                    doProcessPlan(content);
                } else {
                    // do nothing
                }
            } else {
                doProcessPlan(content);
            }
        } else if (PageMonitorPlanType.ScheduleRate.MONTH == scheduleRate) {

        }
    }

    private void doProcessPlan(PageMonitorPlanPO content) {
        String beanName = content.getExecutorBeanName();
        IPlan plan = CDI.current().select(IPlan.class).get();
//        IPlan plan = getApplicationContext().getBean(beanName, IPlan.class);
        PlanReq planReq = new PlanReq();
        planReq.setPageMonitorPlanPO(content);

        String errMsg = null;
        PageMonitorLogPO logPO = new PageMonitorLogPO();
        try {
            PlanResp planResp = plan.doPlan(planReq);
            log.info("planResp:{}", planResp);
            logPO.setScheduleStatus(CommonEnums.YNEnum.Y);
        } catch (Exception e) {
            errMsg = e.getMessage();
            logPO.setScheduleStatus(CommonEnums.YNEnum.N);
        }

        logPO.setPlanId(content.getId());
        logPO.setChangedPageSource(content.getUniqueText());
        logPO.initUserIdAndTime(content.getUserId());
        pageMonitorLogService.getRepository().persist(logPO);

        content.setLastScheduleTime(logPO.getCreateTime());
        content.setLastScheduleStatus(logPO.getScheduleStatus());
        content.setLastScheduleRemark(errMsg);
        content.setUniqueText(logPO.getChangedPageSource());
        getRepository().persist(content);

    }

}
