package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
public interface WeworkGroupBatchDAO extends JpaRepository<WeworkGroupBatchPersist, Long> {

    @Query("select e from WeworkGroupBatchPersist e order by e.id desc limit 1")
    WeworkGroupBatchPersist findLastGroup();
}
