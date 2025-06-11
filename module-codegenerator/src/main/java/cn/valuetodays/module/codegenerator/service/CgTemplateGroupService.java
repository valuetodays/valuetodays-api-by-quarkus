package cn.valuetodays.module.codegenerator.service;

import cn.valuetodays.module.codegenerator.dao.CgTemplateGroupDAO;
import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-02-006
 */
@ApplicationScoped
public class CgTemplateGroupService
    extends BaseCrudService<Long, CgTemplateGroupPersist, CgTemplateGroupDAO> {

}
