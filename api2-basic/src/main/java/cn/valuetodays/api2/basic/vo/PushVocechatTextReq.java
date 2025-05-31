package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送文本消息 - 请求对象.
 *
 * @author lei.liu
 * @since 2024-06-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PushVocechatTextReq extends PushBaseReq {
    @NotNull
    private String content;
    private boolean plainText;
}
