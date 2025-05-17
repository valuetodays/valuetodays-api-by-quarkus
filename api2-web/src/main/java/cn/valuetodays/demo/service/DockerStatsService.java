package cn.valuetodays.demo.service;

import cn.valuetodays.api2.persist.DockerStatsPersist;
import cn.valuetodays.demo.repository.DockerStatsRepository;
import cn.valuetodays.quarkus.commons.base.BaseService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@ApplicationScoped
public class DockerStatsService extends BaseService<Long, DockerStatsPersist, DockerStatsRepository> {

}
