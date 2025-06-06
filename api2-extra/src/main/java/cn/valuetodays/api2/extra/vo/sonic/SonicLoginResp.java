package cn.valuetodays.api2.extra.vo.sonic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SonicLoginResp extends SonicBaseResp<SonicLoginResp.LoginRespData> {
    @Data
    public static class LoginRespData implements Serializable {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expired_in")
        private int expiredIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
    }
}
