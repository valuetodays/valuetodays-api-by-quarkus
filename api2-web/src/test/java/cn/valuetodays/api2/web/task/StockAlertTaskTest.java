package cn.valuetodays.api2.web.task;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StockAlertTask}.
 *
 * @author lei.liu
 * @since 2025-04-25
 */
@QuarkusTest
public class StockAlertTaskTest {
    @Inject
    private StockAlertTask stockAlertTask;

    @Test
    public void scheduleRefreshAfterMarketClose() {
        stockAlertTask.scheduleRefreshAfterMarketClose();
    }

    @Test
    public void scheduleRefreshDailyOffsetChangePtg() {
        stockAlertTask.scheduleRefresh10Min();
    }
}
