package cn.valuetodays.module.codegenerator.service;

import cn.valuetodays.module.codegenerator.dao.CgTemplateDAO;
import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-06-10
 */
@ApplicationScoped
public class CgTemplateService
    extends BaseService<Long, CgTemplatePO, CgTemplateDAO> {

}
