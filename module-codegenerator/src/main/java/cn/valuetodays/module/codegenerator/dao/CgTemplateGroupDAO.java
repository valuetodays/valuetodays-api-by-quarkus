package cn.valuetodays.module.codegenerator.dao;

import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-02-06
 */
@ApplicationScoped
public class CgTemplateGroupDAO implements PanacheRepository<CgTemplateGroupPersist> {
}
