package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liulei
 * @since 2017-11-22 16:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendSmsCodeIO extends SendSmsBaseIO {
    @NotNull
    private String authcode;

}
