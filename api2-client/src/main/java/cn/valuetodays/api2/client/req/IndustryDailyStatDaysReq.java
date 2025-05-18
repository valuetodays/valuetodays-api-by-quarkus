package cn.valuetodays.api2.client.req;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@Data
public class IndustryDailyStatDaysReq implements Serializable {
    private int days;
}
