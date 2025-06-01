package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.enums.NotifyEnums;
import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.valuetodays.api2.basic.vo.NotifyCdciReq;
import cn.vt.R;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-11-20
 */
@Path("/basic/notify")
public class NotifyController {
    @Inject
    private NotifyServiceImpl notifyService;

    @Path("pushToBark")
    @POST
    public R<String> pushToBark(SimpleTypesReq req) {
        String text = req.getText();
        notifyService.notify("测试消息", text, NotifyEnums.Group.TEST.getTitle());
        return R.success();
    }

    @Path("/anon/cdci")
    @POST
    public R<String> cdci(@Valid NotifyCdciReq req) {
        String title = "【CD/CI】" + req.getRepo();
        notifyService.notifyCdci(title, req.getContent());
        return R.success("SUCCESS");
    }

}
