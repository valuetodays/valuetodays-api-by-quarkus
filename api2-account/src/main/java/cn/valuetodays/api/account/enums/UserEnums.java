package cn.valuetodays.api.account.enums;

import cn.vt.core.TitleCapable;
import lombok.Getter;

public final class UserEnums {

    @Getter
    public enum SiteScope implements TitleCapable {
        ADMIN("后台网站"),
        BLOG("博客网站"),
        HAHAHA("h5网站");

        private final String title;

        SiteScope(String title) {
            this.title = title;
        }

    }

    @Getter
    public enum RegOrigin implements TitleCapable {
        PC("pc"),
        IOS("ios"),
        ANDROID("android"),
        ADMIN("网站后台");

        private final String title;

        RegOrigin(String title) {
            this.title = title;
        }
    }

    @Getter
    public enum Status implements TitleCapable {
        NORMAL("正常"),
        APPLYING("申请中"),
        FORBIDDEN("禁用");

        private final String title;

        Status(String title) {
            this.title = title;
        }
    }

}
