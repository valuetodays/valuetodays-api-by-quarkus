package cn.valuetodays.api2.basic.enums;

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
    public enum EnableStatus implements TitleCapable {
        YES("可用"),
        NO("停用");

        private final String title;

        EnableStatus(String title) {
            this.title = title;
        }
    }

}
