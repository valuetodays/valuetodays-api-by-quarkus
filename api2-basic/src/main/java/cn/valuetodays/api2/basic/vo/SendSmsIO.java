package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送短信 - 请求对象.
 * 可自定义任何内容
 *
 * @author lei.liu
 * @since 2022-04-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendSmsIO extends SendSmsBaseIO {
    @NotNull
    private String content;
}
