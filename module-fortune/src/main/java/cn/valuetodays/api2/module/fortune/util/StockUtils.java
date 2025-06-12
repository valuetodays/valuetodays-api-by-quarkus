package cn.valuetodays.api2.module.fortune.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-12
 */
public class StockUtils {
    public static boolean isInTradeTime() {
        return isInTradeTime(LocalDateTime.now().toLocalTime());
    }

    public static boolean isInTradeTime(LocalTime time) {
        return (time.isAfter(LocalTime.of(9, 30)) && time.isBefore(LocalTime.of(11, 30)))
            || (time.isAfter(LocalTime.of(13, 0)) && time.isBefore(LocalTime.of(15, 0)));
    }
}
