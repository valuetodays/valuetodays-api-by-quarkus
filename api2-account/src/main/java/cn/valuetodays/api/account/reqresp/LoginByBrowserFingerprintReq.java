package cn.valuetodays.api.account.reqresp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>该对象对应前端传来的浏览器指纹，且是明文.</p>
 */
@Data
public class LoginByBrowserFingerprintReq implements Serializable {
    @NotBlank(message = "浏览器指纹不能为空")
    private String browserFingerprint;

}
