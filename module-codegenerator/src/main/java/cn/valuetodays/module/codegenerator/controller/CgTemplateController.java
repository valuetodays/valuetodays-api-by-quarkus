package cn.valuetodays.module.codegenerator.controller;

import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.module.codegenerator.service.CgTemplateService;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-06-10
 */
@RequestScoped
@Path("/cg/cgTemplate")
public class CgTemplateController extends BaseCrudController<
    Long, CgTemplatePO,
    CgTemplateService> {
}
