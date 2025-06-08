package cn.valuetodays.api.account.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

public final class UserLoginLogEnums {

    @Getter
    public enum Status implements TitleCapable {
        SUC("成功"),
        FAIL("失败");

        private final String title;

        Status(String title) {
            this.title = title;
        }
    }

}
