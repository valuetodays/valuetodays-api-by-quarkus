package cn.valuetodays.api2.client.enums;

import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-08
 */
public final class WxmpArticleImageEnums {
    @Getter
    public enum Status {
        NOT_RUN("未运行"),
        RUNNING("运行中"),
        DOWNLOAD_DONE("下载完成"),
        POST_DONE("发布完成");

        private final String title;

        Status(String title) {
            this.title = title;
        }

    }
}
