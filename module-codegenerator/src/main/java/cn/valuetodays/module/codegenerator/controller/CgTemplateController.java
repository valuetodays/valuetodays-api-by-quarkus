package cn.valuetodays.module.codegenerator.controller;

import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.module.codegenerator.service.CgTemplateService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-06-10
 */
@RestController
@RequestMapping({"/cg/cgTemplate"})
public class CgTemplateController extends BaseController<
    Long, CgTemplatePO,
    CgTemplateService> {
}
