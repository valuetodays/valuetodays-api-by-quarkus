package cn.valuetodays.api.account.reqresp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>该对象对应前端传来的账号/密码，且都是明文.</p>
 * <p>
 * 前端要做的是：
 * <ol>
 *   <li>请求payload是 {data: 加密函数(stringify({username:xxx, password:yyy}))}</li>
 *   <li>请求header中携带ENCRYPTREQUEST: true</li>
 * </ol>
 */
@Data
public class LoginReq implements Serializable {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    public LoginReq() {
    }
}
