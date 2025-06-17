package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.dao.SanguoEventDAO;
import cn.valuetodays.api2.extra.persist.SanguoEventPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-03-04
 */
@ApplicationScoped
public class SanguoEventServiceImpl
    extends BaseCrudService<Long, SanguoEventPO, SanguoEventDAO> {
}
