package cn.valuetodays.api.account.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

public final class AuthRoleEnums {

    @Getter
    public enum Product implements TitleCapable {
        ROOT("根菜单"),
        EBLOG("易博客"),
        ADMIN("管理系统");

        private final String title;

        Product(String title) {
            this.title = title;
        }
    }

    @Getter
    public enum Status implements TitleCapable {
        NORMAL("正常"),
        DELETE("删除");

        private final String title;

        Status(String title) {
            this.title = title;
        }
    }

}
