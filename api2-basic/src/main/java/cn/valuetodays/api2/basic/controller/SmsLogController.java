package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.persist.SmsLogPO;
import cn.valuetodays.api2.basic.service.SmsLogServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@Path("/common/smsLog")
public class SmsLogController
    extends BaseCrudController<Long, SmsLogPO, SmsLogServiceImpl> {
}
