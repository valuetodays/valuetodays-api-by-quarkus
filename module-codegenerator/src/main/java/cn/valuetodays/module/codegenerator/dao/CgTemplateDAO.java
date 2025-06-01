package cn.valuetodays.module.codegenerator.dao;


import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2022-06-10 14:46
 */
@ApplicationScoped
public class CgTemplateDAO implements PanacheRepository<CgTemplatePO> {

}
