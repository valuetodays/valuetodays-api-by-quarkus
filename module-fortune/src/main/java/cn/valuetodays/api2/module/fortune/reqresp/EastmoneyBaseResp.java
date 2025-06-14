package cn.valuetodays.api2.module.fortune.reqresp;

import java.io.Serializable;

import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-05-31
 */
@Data
public abstract class EastmoneyBaseResp implements Serializable {
    private String version;
    private boolean success;
    private String message;
    private int code;
}
