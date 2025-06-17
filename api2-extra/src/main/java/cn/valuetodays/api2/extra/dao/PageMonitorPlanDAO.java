package cn.valuetodays.api2.extra.dao;

import java.util.List;

import cn.valuetodays.api2.extra.persist.PageMonitorPlanPO;
import cn.valuetodays.api2.web.common.CommonEnums;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@ApplicationScoped
public class PageMonitorPlanDAO implements PanacheRepository<PageMonitorPlanPO> {
    public List<PageMonitorPlanPO> findAllByEnableStatus(CommonEnums.YNEnum enableStatus,
                                                         Page pageable) {
        return find("enableStatus = ?1", enableStatus).page(pageable).list();
    }
}
