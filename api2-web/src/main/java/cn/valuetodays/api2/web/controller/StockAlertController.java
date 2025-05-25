package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.StockAlertPersist;
import cn.valuetodays.api2.web.service.StockAlertService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 股票告警服务
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@RestController
@RequestMapping("/stockAlert")
public class StockAlertController
    extends BaseController<
    Long,
    StockAlertPersist,
    StockAlertService
    > {

}
