package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.IndustryDailyStatPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.Operator;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import cn.valuetodays.quarkus.commons.base.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@ApplicationScoped
public class IndustryDailyStatRepository extends BaseJpaRepository<IndustryDailyStatPersist, Long> {

    protected IndustryDailyStatRepository() {
        super(IndustryDailyStatPersist.class);
    }

    public List<IndustryDailyStatPersist> findAllByStatDate(Integer statDate) {
        return findAll(List.of(QuerySearch.eq("statDate", String.valueOf(statDate))));
//        return find("statDate = ?1", statDate).list();
    }

    //    @Query("select e from IndustryDailyStatPersist e where e.statDate >= :statDate order by statDate asc")
    public List<IndustryDailyStatPersist> findAllByStatDateGe(Integer statDate) {
        List<QuerySearch> qs = List.of(QuerySearch.of("statDate", String.valueOf(statDate), Operator.GTE));
        return findAll(qs, new Sort[]{Sort.ofAsc("statDate")});
//        return find("statDate >= ?1", Sort.ascending("statDate"), statDate).list();
    }
}
