package cn.valuetodays.api2.extra.service.plan;

import java.io.Serializable;

import cn.valuetodays.api2.extra.persist.PageMonitorPlanPO;
import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-02-08
 */
@Data
public class PlanResp implements Serializable {
    private boolean changed;
    private PageMonitorPlanPO pageMonitorPlanPO;

}
