package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.client.persist.DockerStatsPersist;
import cn.valuetodays.api2.web.repository.DockerStatsRepository;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@ApplicationScoped
public class DockerStatsService extends BaseCrudService<Long, DockerStatsPersist, DockerStatsRepository> {

}
