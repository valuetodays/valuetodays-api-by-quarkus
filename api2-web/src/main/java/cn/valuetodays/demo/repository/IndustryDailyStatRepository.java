package cn.valuetodays.demo.repository;


import cn.valuetodays.api2.persist.IndustryDailyStatPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@Repository
public interface IndustryDailyStatRepository extends JpaRepository<IndustryDailyStatPersist, Long> {

}
