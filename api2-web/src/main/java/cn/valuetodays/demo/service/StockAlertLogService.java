package cn.valuetodays.demo.service;

import cn.valuetodays.api2.persist.StockAlertLogPersist;
import cn.valuetodays.demo.repository.StockAlertLogDAO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * 股票告警记录表
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@ApplicationScoped
@Slf4j
public class StockAlertLogService
    extends BaseService<Long, StockAlertLogPersist, StockAlertLogDAO> {

}
