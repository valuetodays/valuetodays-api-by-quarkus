package cn.valuetodays.demo.service;

import cn.valuetodays.demo.persist.DockerStatsPersist;
import cn.valuetodays.demo.repository.DockerStatsRepository;
import jakarta.inject.Singleton;
import valuetodays.demo.commons.base.BaseService;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Singleton
public class DockerStatsService extends BaseService<Long, DockerStatsPersist, DockerStatsRepository> {

}
