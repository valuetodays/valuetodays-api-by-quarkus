package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * 发送文件 - 请求对象.
 *
 * @author lei.liu
 * @since 2024-06-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PushVocechatFileReq extends PushBaseReq {
    @NotNull
    private String fileName;
    private File file;
}
