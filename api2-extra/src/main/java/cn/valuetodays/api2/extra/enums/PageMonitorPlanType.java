package cn.valuetodays.api2.extra.enums;

import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-02-08
 */
public final class PageMonitorPlanType {
    public enum ScheduleRate {
        MONTH("按月更新"),
        WEEK("按周更新"),
        DAY("按天更新");

        private final String value;

        ScheduleRate(String value) {
            this.value = value;
        }

        public String getInfo() {
            return this.value;
        }
    }

    @Data
    public static class Conf {
        private ScheduleRate scheduleRate;
    }
}
