package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.api2.web.component.GithubComponent;
import cn.valuetodays.api2.web.service.WxmpArticleImageService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import cn.vt.R;
import cn.vt.exception.AssertUtils;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-25
 */
@RestController
@RequestMapping("/wxmpArticleImage")
public class WxmpArticleImageController
    extends BaseController<
    Long,
    WxmpArticleImagePersist,
    WxmpArticleImageService
    > {
    @Inject
    ManagedExecutor managedExecutor;
    @Inject
    GithubComponent githubComponent;

    @GetMapping("/scheduleDownloadImage")
    public R<String> scheduleDownloadImage() {
        managedExecutor.execute(() -> service.scheduleDownloadImage());
        return R.success("SUCCESS");
    }

    @PostMapping("/uploadImageByWxmpUrl.do")
    public List<String> uploadImageByWxmpUrl(@RequestBody SimpleTypesReq req) {
        String url = req.getText();
        AssertUtils.assertStringNotBlank(url, "text不能为空");

        return githubComponent.uploadImageByWxmpUrl(url).getItem2();
    }
}
