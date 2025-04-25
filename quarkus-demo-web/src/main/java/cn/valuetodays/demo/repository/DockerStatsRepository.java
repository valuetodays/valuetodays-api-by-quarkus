package cn.valuetodays.demo.repository;

import cn.valuetodays.demo.persist.DockerStatsPersist;
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
