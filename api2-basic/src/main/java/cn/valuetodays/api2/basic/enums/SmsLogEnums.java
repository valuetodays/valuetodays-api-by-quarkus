package cn.valuetodays.api2.basic.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-25
 */
public final class SmsLogEnums {
    @Getter
    public enum StatusEnum implements TitleCapable {
        SUC("成功"),
        FAIL("失败");

        private final String title;

        StatusEnum(String title) {
            this.title = title;
        }
    }

}
