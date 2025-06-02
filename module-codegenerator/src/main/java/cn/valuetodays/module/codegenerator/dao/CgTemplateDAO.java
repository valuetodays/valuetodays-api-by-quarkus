package cn.valuetodays.module.codegenerator.dao;


import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2022-06-10 14:46
 */
@ApplicationScoped
public class CgTemplateDAO extends BaseJpaRepository<CgTemplatePO, Long> {

    protected CgTemplateDAO() {
        super(CgTemplatePO.class);
    }
}
