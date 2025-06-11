package cn.valuetodays.module.spider.controller;

import cn.valuetodays.module.spider.client.persist.WxmpAuthorPO;
import cn.valuetodays.module.spider.service.impl.wxmp.WxmpAuthorServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@Slf4j
@Path("/wxmpAuthor")
public class WxmpAuthorController
    extends BaseCrudController<Long, WxmpAuthorPO, WxmpAuthorServiceImpl> {
}
