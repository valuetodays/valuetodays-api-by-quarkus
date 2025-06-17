package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.dao.PageMonitorLogDAO;
import cn.valuetodays.api2.extra.persist.PageMonitorLogPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 页面监控日志
 *
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@ApplicationScoped
public class PageMonitorLogServiceImpl
    extends BaseCrudService<Long, PageMonitorLogPO, PageMonitorLogDAO> {

}
