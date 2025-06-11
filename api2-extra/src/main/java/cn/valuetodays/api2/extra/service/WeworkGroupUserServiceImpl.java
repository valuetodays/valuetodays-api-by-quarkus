package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.dao.WeworkGroupUserDAO;
import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@ApplicationScoped
public class WeworkGroupUserServiceImpl
    extends BaseCrudService<Long, WeworkGroupUserPersist, WeworkGroupUserDAO> {
}
