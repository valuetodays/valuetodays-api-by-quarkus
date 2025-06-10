package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-10
 */
@Data
public class EncryptPasswordResp implements Serializable {
    private String encryptedPassword;
}
