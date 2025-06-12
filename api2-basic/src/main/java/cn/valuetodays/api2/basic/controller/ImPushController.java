package cn.valuetodays.api2.basic.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-12
 */
@Path("/basic/imPush")
public class ImPushController {
//    @Inject
//    VocechatService vocechatService;

    @Path("/public/vocechat/webhook")
    @GET
    public Boolean vocechatWebhookGet() {
        return true;
    }

}
