package cn.valuetodays.api2.basic;

import java.util.List;

import io.smallrye.config.ConfigMapping;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-06-20
 */
@ConfigMapping(prefix = "vt.vocechat")
public interface VocechatProperties {
    String basePath();

    String urlSendToGroup();

    String urlSendToUser();

    List<Bot> botList();

    interface Bot {
        Integer uid();

        String title();

        String apiKey();
    }

}
