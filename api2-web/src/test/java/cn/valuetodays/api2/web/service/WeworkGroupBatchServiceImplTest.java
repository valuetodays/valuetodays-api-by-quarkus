package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.api2.extra.service.WeworkGroupBatchServiceImpl;
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

    @Test
    public void findLastGroup() {
        WeworkGroupBatchPersist lastGroup = weworkGroupBatchService.getRepository().findLastGroup();
        MatcherAssert.assertThat(lastGroup, CoreMatchers.notNullValue());
    }
}
