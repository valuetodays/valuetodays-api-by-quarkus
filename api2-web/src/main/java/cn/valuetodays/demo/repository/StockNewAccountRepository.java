package cn.valuetodays.demo.repository;

import cn.valuetodays.api2.persist.StockNewAccountPersist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-25
 */
public interface StockNewAccountRepository extends JpaRepository<StockNewAccountPersist, Long> {
}
