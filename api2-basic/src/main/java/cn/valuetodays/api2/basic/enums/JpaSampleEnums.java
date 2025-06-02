package cn.valuetodays.api2.basic.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-02
 */
public final class JpaSampleEnums {
    @Getter
    public enum Type implements TitleCapable {
        EASY("容易"),
        NORMAL("正常"),
        HARD("困难");

        private final String title;

        Type(String title) {
            this.title = title;
        }

    }
}
