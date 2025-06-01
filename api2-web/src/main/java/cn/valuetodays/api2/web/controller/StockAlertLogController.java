package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.StockAlertLogPersist;
import cn.valuetodays.api2.web.service.StockAlertLogService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Path;

/**
 * 股票告警记录表服务
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@RequestScoped
@Path("/stockAlertLog")
public class StockAlertLogController
    extends BaseController<
    Long,
    StockAlertLogPersist,
    StockAlertLogService
    > {

}
