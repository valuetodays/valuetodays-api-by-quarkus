package cn.valuetodays.module.spider.controller;

import cn.valuetodays.module.spider.client.reqresp.WxmpArticleGatherReq;
import cn.valuetodays.module.spider.client.reqresp.WxmpArticleGatherResp;
import cn.valuetodays.module.spider.service.impl.wxmp.WxmpArticleServiceImpl;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@Slf4j
@Path("/wxmpArticle")
public class WxmpArticleController {
    @Inject
    WxmpArticleServiceImpl wxmpArticleService;

    @POST
    @Path("gather")
    public WxmpArticleGatherResp gather(@Valid WxmpArticleGatherReq req) {
        return wxmpArticleService.gather(req);
    }
}
