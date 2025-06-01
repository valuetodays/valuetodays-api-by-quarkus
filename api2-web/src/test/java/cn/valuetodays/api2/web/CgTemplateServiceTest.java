package cn.valuetodays.api2.web;

import cn.valuetodays.module.codegenerator.dao.CgTemplateDAO;
import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.module.codegenerator.service.CgTemplateService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CgTemplateDAO}.
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@QuarkusTest
public class CgTemplateServiceTest {
    @Inject
    CgTemplateService cgTemplateService;

    @Test
    public void test() {
        CgTemplatePO entity = new CgTemplatePO();
        entity.setTitle("test");
        entity.setCode("sdfsdfsdf");
        entity.setDstDir("aaa");
        entity.setFileName("bbb");
        entity.initUserIdAndTime(1L);
        cgTemplateService.save(entity);
    }

}
