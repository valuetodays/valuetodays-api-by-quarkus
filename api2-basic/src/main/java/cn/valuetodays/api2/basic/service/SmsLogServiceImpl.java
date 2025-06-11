package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.dao.SmsLogDAO;
import cn.valuetodays.api2.basic.persist.SmsLogPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@ApplicationScoped
public class SmsLogServiceImpl
    extends BaseCrudService<Long, SmsLogPO, SmsLogDAO> {

}
