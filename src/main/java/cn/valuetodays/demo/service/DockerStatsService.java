package cn.valuetodays.demo.service;

import cn.valuetodays.demo.base.BaseService;
import cn.valuetodays.demo.persist.DockerStatsPersist;
import cn.valuetodays.demo.repository.DockerStatsRepository;
import jakarta.inject.Singleton;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Singleton
public class DockerStatsService extends BaseService<Long, DockerStatsPersist, DockerStatsRepository> {

}
