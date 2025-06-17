package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.PageMonitorLogPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@ApplicationScoped
public class PageMonitorLogDAO implements PanacheRepository<PageMonitorLogPO> {

}
