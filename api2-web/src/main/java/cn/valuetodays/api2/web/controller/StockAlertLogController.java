package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.StockAlertLogPersist;
import cn.valuetodays.api2.web.service.StockAlertLogService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 股票告警记录表服务
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@RestController
@RequestMapping("/stockAlertLog")
public class StockAlertLogController
    extends BaseController<
    Long,
    StockAlertLogPersist,
    StockAlertLogService
    > {

}
