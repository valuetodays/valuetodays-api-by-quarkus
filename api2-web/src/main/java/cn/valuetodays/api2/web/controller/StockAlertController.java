package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.StockAlertPersist;
import cn.valuetodays.api2.web.service.StockAlertService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import jakarta.ws.rs.Path;

/**
 * 股票告警服务
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@Path("/stockAlert")
public class StockAlertController
    extends BaseController<
    Long,
    StockAlertPersist,
    StockAlertService
    > {

}
