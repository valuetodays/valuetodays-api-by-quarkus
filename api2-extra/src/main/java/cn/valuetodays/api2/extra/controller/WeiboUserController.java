package cn.valuetodays.api2.extra.controller;

import cn.valuetodays.api2.extra.persist.WeiboUserPO;
import cn.valuetodays.api2.extra.service.WeiboUserServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.Path;

/**
 * 服务
 *
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@Path("/weiboUser")
public class WeiboUserController extends BaseCrudController<
    Long, WeiboUserPO, WeiboUserServiceImpl> {

}
