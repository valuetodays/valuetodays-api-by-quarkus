package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.DockerStatsPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Repository
public interface DockerStatsRepository extends JpaRepository<DockerStatsPersist, Long> {
}
