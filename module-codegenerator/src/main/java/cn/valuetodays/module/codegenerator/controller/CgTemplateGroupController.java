package cn.valuetodays.module.codegenerator.controller;

import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import cn.valuetodays.module.codegenerator.service.CgTemplateGroupService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-03-06
 */
@RestController
@RequestMapping({"/cg/cgTemplateGroup"})
public class CgTemplateGroupController extends BaseController<
    Long, CgTemplateGroupPersist,
    CgTemplateGroupService> {
}
