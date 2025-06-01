package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import cn.valuetodays.api2.extra.service.WeworkGroupBatchServiceImpl;
import cn.valuetodays.api2.extra.service.WeworkGroupUserServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WeworkGroupBatchServiceImpl}.
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@QuarkusTest
public class WeworkGroupBatchServiceImplTest {

    @Inject
    WeworkGroupBatchServiceImpl weworkGroupBatchService;
    @Inject
    WeworkGroupUserServiceImpl weworkGroupUserService;

    @Test
    public void findLastGroup() {
        WeworkGroupBatchPersist lastGroup = weworkGroupBatchService.getRepository().findLastGroup();
        MatcherAssert.assertThat(lastGroup, CoreMatchers.notNullValue());
    }

    @Test
    public void findTop1ByEmailOrderByCreateTimeDesc() {
        WeworkGroupUserPersist lastGroup = weworkGroupUserService.getRepository()
            .findTop1ByEmailOrderByCreateTimeDesc("aaa");
        MatcherAssert.assertThat(lastGroup, CoreMatchers.nullValue());
        lastGroup = weworkGroupUserService.getRepository()
            .findTop1ByEmailOrderByCreateTimeDesc("001@youshucorp.cn");
        MatcherAssert.assertThat(lastGroup, CoreMatchers.notNullValue());
    }

    @Test
    public void findTop1ByEmailOrderByCreateTimeAsc() {
        WeworkGroupUserPersist lastGroup = weworkGroupUserService.getRepository()
            .findTop1ByEmailOrderByCreateTimeAsc("aaa");
        MatcherAssert.assertThat(lastGroup, CoreMatchers.nullValue());
        lastGroup = weworkGroupUserService.getRepository()
            .findTop1ByEmailOrderByCreateTimeAsc("001@youshucorp.cn");
        MatcherAssert.assertThat(lastGroup, CoreMatchers.notNullValue());
    }
}
