package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.StockNewAccountPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-25
 */
@ApplicationScoped
public class StockNewAccountRepository extends BaseJpaRepository<StockNewAccountPersist, Long> {
    protected StockNewAccountRepository() {
        super(StockNewAccountPersist.class);
    }
}
