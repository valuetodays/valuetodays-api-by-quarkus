package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-11-20
 */
@Data
public class BarkDict implements Serializable {
    private String url;
    private String deviceId;
}
