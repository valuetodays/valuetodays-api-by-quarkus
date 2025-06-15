package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.service.VocechatServiceImpl;
import cn.valuetodays.api2.basic.vo.PushVocechatFileReq;
import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import cn.valuetodays.api2.basic.vo.VocechatWebhookReq;
import cn.valuetodays.quarkus.commons.base.RunAsync;
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
public class ImPushController extends RunAsync {

    @Inject
    VocechatServiceImpl vocechatService;

    /**
     * todo 该方法不要返回R？
     */
    @Path("/public/vocechat/webhook")
    @GET
    @Blocking
    public Boolean vocechatWebhookGet() {
        return true;
    }

    /**
     * todo 该方法不要返回R？
     */
    @Path("/public/vocechat/webhook")
    @POST
    public Boolean vocechatWebhookPost(VocechatWebhookReq req) {
        super.executeAsync(() -> {
            vocechatService.processWebhook(req);
        });
        return true;
    }

    @Path("/vocechat/plainText")
    @POST
    public Boolean pushVocechatPlainText(PushVocechatTextReq req) {
        super.executeAsync(() -> {
            req.setPlainText(true);
            vocechatService.pushVocechatText(req);
        });
        return true;
    }

    @Path("/vocechat/markdownText")
    @POST
    public Boolean pushVocechatMarkdownText(PushVocechatTextReq req) {
        super.executeAsync(() -> {
            req.setPlainText(false);
            vocechatService.pushVocechatText(req);
        });
        return true;
    }

    @Path("/vocechat/pushVocechatFile")
    @POST
    public Boolean pushVocechatFile(PushVocechatFileReq req) {
        super.executeAsync(() -> {
            vocechatService.pushVocechatFile(req);
        });
        return true;
    }

}
