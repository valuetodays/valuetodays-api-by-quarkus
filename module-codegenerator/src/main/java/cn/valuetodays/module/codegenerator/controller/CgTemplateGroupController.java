package cn.valuetodays.module.codegenerator.controller;

import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import cn.valuetodays.module.codegenerator.service.CgTemplateGroupService;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-03-06
 */
@RequestScoped
@Path("/cg/cgTemplateGroup")
public class CgTemplateGroupController extends BaseCrudController<
    Long, CgTemplateGroupPersist,
    CgTemplateGroupService> {
}
