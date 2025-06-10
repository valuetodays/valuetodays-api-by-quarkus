package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-10
 */
@Data
public class EncryptPasswordReq implements Serializable {
    @NotNull
    private String rawPassword;
}
