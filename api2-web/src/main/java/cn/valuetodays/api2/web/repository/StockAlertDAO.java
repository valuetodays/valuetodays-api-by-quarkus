package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.enums.StockAlertEnums;
import cn.valuetodays.api2.client.persist.StockAlertPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@ApplicationScoped
public class StockAlertDAO extends BaseJpaRepository<StockAlertPersist, Long> {

    protected StockAlertDAO() {
        super(StockAlertPersist.class);
    }

    public List<StockAlertPersist> findAllByStatus(StockAlertEnums.Status status) {
        return findAll(List.of(QuerySearch.eq("status", status.name())));
//        return list("status = ?1", status);
    }

    public List<StockAlertPersist> findAllByStatusAndScheduleType(StockAlertEnums.Status status,
                                                                  StockAlertEnums.ScheduleType scheduleType) {
        List<QuerySearch> qs = List.of(
            QuerySearch.eq("status", status.name()),
            QuerySearch.eq("scheduleType", scheduleType.name())
        );
        return findAll(qs);
//        return list("status = ?1 and scheduleType = ?2", status, scheduleType);
    }
}
