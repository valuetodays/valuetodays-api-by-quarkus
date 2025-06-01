package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
@ApplicationScoped
public class WeworkGroupBatchDAO implements PanacheRepository<WeworkGroupBatchPersist> {

    //    @Query("select e from WeworkGroupBatchPersist e order by e.id desc limit 1")
    public WeworkGroupBatchPersist findLastGroup() {
        return list("order by id desc limit 1").stream().findFirst().orElse(null);
    }
}
