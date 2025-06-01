package cn.valuetodays.module.codegenerator.dao;


import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lei.liu
 * @since 2022-06-10 14:46
 */
public interface CgTemplateDAO extends JpaRepository<CgTemplatePO, Long> {

}
