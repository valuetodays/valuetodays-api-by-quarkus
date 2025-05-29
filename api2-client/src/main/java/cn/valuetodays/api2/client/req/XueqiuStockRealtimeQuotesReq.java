package cn.valuetodays.api2.client.req;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-19
 */
@Data
public class XueqiuStockRealtimeQuotesReq implements Serializable {
    private String codes;
    private long ts = System.currentTimeMillis();
}
