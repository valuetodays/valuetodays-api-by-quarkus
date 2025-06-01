package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.IndustryDailyStatPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@ApplicationScoped
public class IndustryDailyStatRepository implements PanacheRepository<IndustryDailyStatPersist> {

    public List<IndustryDailyStatPersist> findAllByStatDate(Integer statDate) {
        return find("statDate = ?1", statDate).list();
    }

    //    @Query("select e from IndustryDailyStatPersist e where e.statDate >= :statDate order by statDate asc")
    public List<IndustryDailyStatPersist> findAllByStatDateGe(Integer statDate) {
        return find("statDate >= ?1", Sort.ascending("statDate"), statDate).list();
    }
}
