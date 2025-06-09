package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-09
 */
@Data
public class GetPublicKeyResp implements Serializable {
    private String pubKey;
    private String keyId;
}
