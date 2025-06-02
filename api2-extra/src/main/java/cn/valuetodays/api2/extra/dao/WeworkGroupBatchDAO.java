package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.Sort;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
@ApplicationScoped
public class WeworkGroupBatchDAO extends BaseJpaRepository<WeworkGroupBatchPersist, Long> {

    protected WeworkGroupBatchDAO() {
        super(WeworkGroupBatchPersist.class);
    }

    //    @Query("select e from WeworkGroupBatchPersist e order by e.id desc limit 1")
    public WeworkGroupBatchPersist findLastGroup() {
        return findOne(null, new Sort[]{Sort.ofDesc("id")}, 1);
    }
}
