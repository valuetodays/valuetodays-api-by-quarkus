package cn.valuetodays.api2.extra.controller;

import cn.valuetodays.api2.extra.persist.WeiboPO;
import cn.valuetodays.api2.extra.service.WeiboServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.Path;

/**
 * 微博服务
 *
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@Path("/weibo")
public class WeiboController extends BaseCrudController<
    Long, WeiboPO, WeiboServiceImpl> {

}
