package cn.valuetodays.module.codegenerator.dao;

import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-02-06
 */
@ApplicationScoped
public class CgTemplateGroupDAO extends BaseJpaRepository<CgTemplateGroupPersist, Long> {
    protected CgTemplateGroupDAO() {
        super(CgTemplateGroupPersist.class);
    }
}
