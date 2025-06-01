package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.StockNewAccountPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-25
 */
@ApplicationScoped
public class StockNewAccountRepository implements PanacheRepository<StockNewAccountPersist> {
}
