package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.persist.IndustryDailyStatPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@Repository
public interface IndustryDailyStatRepository extends JpaRepository<IndustryDailyStatPersist, Long> {

    List<IndustryDailyStatPersist> findAllByStatDate(Integer statDate);

    @Query("select e from IndustryDailyStatPersist e where e.statDate >= :statDate order by statDate asc")
    List<IndustryDailyStatPersist> findAllByStatDateGe(Integer statDate);
}
