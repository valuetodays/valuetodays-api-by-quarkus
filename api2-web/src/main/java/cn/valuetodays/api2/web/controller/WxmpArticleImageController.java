package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.api2.web.service.WxmpArticleImageService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
