package cn.valuetodays.api2.web;
import java.util.ArrayList;

import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
import cn.valuetodays.api2.basic.service.JpaSampleService;
import cn.valuetodays.quarkus.commons.base.Operator;
import cn.valuetodays.quarkus.commons.base.PageQueryReqIO;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import cn.valuetodays.quarkus.commons.base.Sort;
import cn.vt.web.RestPageImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-02
 */
@QuarkusTest
@Slf4j
public class JpaSampleServiceTest {
    @Inject
    JpaSampleService jpaSampleService;

    @Test
    public void list() {
        List<JpaSamplePersist> list = jpaSampleService.list();
        log.info("list.size()={}", list.size());
    }

    @Test
    public void listByIds() {
        List<JpaSamplePersist> list = jpaSampleService.listByIds(List.of(1L,2L,3L,4L));
        log.info("list.size()={}", list.size());
    }

    @Test
    public void listBy() {
        QuerySearch qs1 = QuerySearch.of("nameCn", "张三", Operator.EQ);
        QuerySearch qs2 = QuerySearch.of("status", "NORMAL", Operator.EQ);
        List<JpaSamplePersist> list = jpaSampleService.listBy(List.of(qs1, qs2));
        log.info("list.size()={}", list.size());
    }

    @Test
    public void query() {
        QuerySearch qs1 = QuerySearch.of("nameCn", "张三", Operator.EQ);
        QuerySearch qs2 = QuerySearch.of("status", "NORMAL", Operator.EQ);
        PageQueryReqIO pageQueryReqIO = new PageQueryReqIO();
        pageQueryReqIO.setSearches(List.of(qs1, qs2));
        pageQueryReqIO.setSorts(List.of(Sort.ofDesc("id")));
        pageQueryReqIO.setPageNum(1);
        pageQueryReqIO.setPageSize(10);
        RestPageImpl<JpaSamplePersist> paged = jpaSampleService.query(pageQueryReqIO);
        log.info("paged={}", paged);
    }
}
