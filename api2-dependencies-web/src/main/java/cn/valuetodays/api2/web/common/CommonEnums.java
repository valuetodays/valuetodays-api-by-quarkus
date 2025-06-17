package cn.valuetodays.api2.web.common;

import cn.vt.core.TitleCapable;
import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-09
 */
public final class CommonEnums {


    @Getter
    public enum YNEnum implements TitleCapable {
        Y("是"),
        N("否");

        private final String title;

        YNEnum(String title) {
            this.title = title;
        }
    }

    @Getter
    public enum EnableStatus implements TitleCapable {
        YES("可用"),
        NO("停用");

        private final String title;

        EnableStatus(String title) {
            this.title = title;
        }
    }

}
