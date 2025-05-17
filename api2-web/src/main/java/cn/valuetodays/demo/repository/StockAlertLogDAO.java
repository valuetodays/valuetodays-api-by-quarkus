package cn.valuetodays.demo.repository;


import cn.valuetodays.api2.persist.StockAlertLogPersist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
public interface StockAlertLogDAO extends JpaRepository<StockAlertLogPersist, Long> {

}
