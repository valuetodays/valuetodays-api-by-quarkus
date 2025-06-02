package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.DockerStatsPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@ApplicationScoped
public class DockerStatsRepository extends BaseJpaRepository<DockerStatsPersist, Long> {
    protected DockerStatsRepository() {
        super(DockerStatsPersist.class);
    }
}
