package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.enums.NotifyEnums;
import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.valuetodays.api2.basic.vo.NotifyCdciReq;
import cn.vt.R;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-11-20
 */
@RestController
@RequestMapping("/basic/notify")
public class NotifyController {
    @Inject
    private NotifyServiceImpl notifyService;

    @PostMapping("pushToBark")
    public R<String> pushToBark(@RequestBody SimpleTypesReq req) {
        String text = req.getText();
        notifyService.notify("测试消息", text, NotifyEnums.Group.TEST.getTitle());
        return R.success();
    }

    @PostMapping("/anon/cdci")
    public R<String> cdci(@RequestBody @Valid NotifyCdciReq req) {
        String title = "【CD/CI】" + req.getRepo();
        notifyService.notifyCdci(title, req.getContent());
        return R.success("SUCCESS");
    }

}
