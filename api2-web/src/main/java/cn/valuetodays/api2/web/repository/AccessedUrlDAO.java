package cn.valuetodays.api2.web.repository;

import cn.valuetodays.quote.persist.AccessedUrlPersist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lei.liu
 * @since 2025-03-27 16:01
 */
public interface AccessedUrlDAO extends JpaRepository<AccessedUrlPersist, Long> {

}
