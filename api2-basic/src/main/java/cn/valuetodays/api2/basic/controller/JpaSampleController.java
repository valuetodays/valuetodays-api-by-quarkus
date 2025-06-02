package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
import cn.valuetodays.api2.basic.service.JpaSampleService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import jakarta.ws.rs.Path;

/**
 * jpa查询示例服务
 *
 * @author lei.liu
 * @since 2025-06-02 07:43
 */
@Path("/basic/jpaSample")
public class JpaSampleController
    extends BaseController<Long, JpaSamplePersist, JpaSampleService> {

}
