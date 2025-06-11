package cn.valuetodays.api2.web;

import cn.valuetodays.module.codegenerator.dao.CgTemplateDAO;
import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.module.codegenerator.service.CgTemplateService;
import cn.valuetodays.quarkus.commons.base.Operator;
import cn.valuetodays.quarkus.commons.base.PageQueryReqIO;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import cn.vt.web.RestPageImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for {@link CgTemplateDAO}.
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@QuarkusTest
@Slf4j
public class CgTemplateServiceTest {
    @Inject
    CgTemplateService cgTemplateService;

    @Test
    public void query() {
        PageQueryReqIO req = new PageQueryReqIO();
        req.setPageNum(1);
        req.setPageSize(10);
        List<QuerySearch> searches = List.of(
            QuerySearch.of("title", "quarkus", Operator.LIKE)
        );
        req.setSearches(searches);
        RestPageImpl<CgTemplatePO> query = cgTemplateService.query(req);
        log.info("query={}", query);
        MatcherAssert.assertThat(query, CoreMatchers.notNullValue());
    }

    @Test
    public void save() {
        CgTemplatePO entity = new CgTemplatePO();
        entity.setTitle("test");
        entity.setCode("sdfsdfsdf");
        entity.setDstDir("aaa");
        entity.setFileName("bbb");
        entity.initUserIdAndTime(1L);
        cgTemplateService.save(entity);
    }

}
