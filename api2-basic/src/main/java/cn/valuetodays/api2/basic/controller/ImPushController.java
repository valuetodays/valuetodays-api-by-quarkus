package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.service.VocechatServiceImpl;
import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import cn.valuetodays.api2.basic.vo.VocechatWebhookReq;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-12
 */
@Path("/basic/imPush")
public class ImPushController {

    @Inject
    VocechatServiceImpl vocechatService;

    /**
     * todo 该方法不要返回R？
     */
    @Path("/public/vocechat/webhook")
    @GET
    public Boolean vocechatWebhookGet() {
        return true;
    }

    /**
     * todo 该方法不要返回R？
     */
    @Path("/public/vocechat/webhook")
    @POST
    @Blocking
    public Boolean vocechatWebhookPost(VocechatWebhookReq req) {
        new Thread(
            () -> {
                vocechatService.processWebhook(req);
            }
        ).start();
        return true;
    }

    @Path("/vocechat/plainText")
    @POST
    @Blocking
    public Boolean pushVocechatPlainText(PushVocechatTextReq req) {
        req.setPlainText(true);
        return vocechatService.pushVocechatText(req);
    }

    @Path("/vocechat/markdownText")
    @POST
    @Blocking
    public Boolean pushVocechatMarkdownText(PushVocechatTextReq req) {
        req.setPlainText(false);
        return vocechatService.pushVocechatText(req);
    }

}
