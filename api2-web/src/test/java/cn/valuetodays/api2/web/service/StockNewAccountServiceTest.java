package cn.valuetodays.api2.web.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@QuarkusTest
public class StockNewAccountServiceTest extends BaseTest {
    @Inject
    private StockNewAccountService stockNewAccountService;

    @Test
    public void refresh() {
        stockNewAccountService.refresh();
    }

    @Test
    public void refreshWithYearMonth() {
        YearMonth yearMonth = YearMonth.of(2021, 12);
        while (yearMonth.getYear() < LocalDate.now().getYear()) {
            stockNewAccountService.refresh(yearMonth);
            yearMonth = yearMonth.plusYears(1);
        }
    }
}
