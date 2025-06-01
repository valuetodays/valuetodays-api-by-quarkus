package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.StockAlertLogPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@ApplicationScoped
public class StockAlertLogDAO implements PanacheRepository<StockAlertLogPersist> {

}
