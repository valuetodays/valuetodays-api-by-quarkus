package cn.valuetodays.demo.repository;

import cn.valuetodays.demo.persist.StockAlertPersist;
import cn.valuetodays.demo.persist.enums.StockAlertEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
public interface StockAlertDAO extends JpaRepository<StockAlertPersist, Long> {

    List<StockAlertPersist> findAllByStatus(StockAlertEnums.Status status);

    List<StockAlertPersist> findAllByStatusAndScheduleType(StockAlertEnums.Status status, StockAlertEnums.ScheduleType scheduleType);
}
