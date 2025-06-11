package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liulei
 * @since 2017-11-22 16:02
 */
@Data
public class VerifySmsCodeIO implements Serializable {
    @NotNull
    private String mobile;
    @NotNull
    private String smscode;

}
