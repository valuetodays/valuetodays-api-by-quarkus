package cn.valuetodays.api2.extra.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-03-04
 */
public final class SanguoEventEnums {
    @Getter
    public enum TypeEnum implements TitleCapable {
        PERSON("人物"),
        EVENT("事件");

        private final String title;

        TypeEnum(String title) {
            this.title = title;
        }
    }

    @Getter
    public enum BelongsEnum implements TitleCapable {
        WEI("魏"),
        SHU("蜀"),
        WU("吴"),
        TA("他"),
        JIN("晋");

        private final String title;

        BelongsEnum(String title) {
            this.title = title;
        }
    }

}
