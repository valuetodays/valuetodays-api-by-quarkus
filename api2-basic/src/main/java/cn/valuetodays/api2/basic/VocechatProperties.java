package cn.valuetodays.api2.basic;

import java.util.Objects;

import cn.valuetodays.api2.basic.vo.PushBaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-06-20
 */
@Data
@ConfigurationProperties(prefix = "vt.vocechat", ignoreUnknownFields = false)
public class VocechatProperties {
    private String basePath;
    private String urlSendToGroup;
    private String urlSendToUser;
    private String apiKey;
    private String botFortuneApiKey;

    @JsonIgnore
    public String buildUrl(PushBaseReq req) {
        String url = null;
        Integer toGroupId = req.getToGroupId();
        if (Objects.nonNull(toGroupId)) {
            url = this.getUrlSendToGroup() + toGroupId;
        }
        Integer toUserId = req.getToUserId();
        if (Objects.nonNull(toUserId)) {
            url = this.getUrlSendToUser() + toUserId;
        }

        return url;
    }
}
