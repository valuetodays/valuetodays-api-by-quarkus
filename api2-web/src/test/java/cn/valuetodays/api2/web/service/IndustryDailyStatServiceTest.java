package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.module.fortune.service.IndustryDailyStatService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link IndustryDailyStatService}.
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@QuarkusTest
public class IndustryDailyStatServiceTest {

    @Inject
    private IndustryDailyStatService industryDailyStatService;

    @Test
    public void refresh() {
        industryDailyStatService.refresh();
    }
}
