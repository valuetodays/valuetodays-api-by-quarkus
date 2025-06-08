package cn.valuetodays.api.account.reqresp;

import lombok.Data;

import java.io.Serializable;

/**
 * token信息，该信息用户返回给前端，前端请求携带accessToken进行用户校验
 */
@Data
public class TokenInfoVO implements Serializable {

    private String accessToken;

    private String refreshToken;

    private Integer expiresIn = 3600;

}
