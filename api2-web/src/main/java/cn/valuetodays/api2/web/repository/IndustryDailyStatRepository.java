package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.IndustryDailyStatPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
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
        return null; // todo
    }

    //    @Query("select e from IndustryDailyStatPersist e where e.statDate >= :statDate order by statDate asc")
    public List<IndustryDailyStatPersist> findAllByStatDateGe(Integer statDate) {
        return null; //todo
    }
}
