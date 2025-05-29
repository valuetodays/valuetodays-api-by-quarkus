package cn.valuetodays.api2.client.req;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@Data
public class PullCookieReq implements Serializable {
    private String domain;
}
