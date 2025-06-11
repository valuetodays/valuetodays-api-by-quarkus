package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.dao.JpaSampleRepository;
import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;


/**
 * jpa查询示例
 *
 * @author lei.liu
 * @since 2025-06-02 07:43
 */
@ApplicationScoped
@Slf4j
public class JpaSampleService
    extends BaseCrudService<Long, JpaSamplePersist, JpaSampleRepository> {
}

