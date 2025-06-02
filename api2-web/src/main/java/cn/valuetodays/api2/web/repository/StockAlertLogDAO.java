package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.StockAlertLogPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@ApplicationScoped
public class StockAlertLogDAO extends BaseJpaRepository<StockAlertLogPersist, Long> {

    protected StockAlertLogDAO() {
        super(StockAlertLogPersist.class);
    }
}
