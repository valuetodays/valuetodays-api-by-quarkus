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
public class XueqiuStockKlineReq implements Serializable {
    private String codeWithRegion;
    private long begin = System.currentTimeMillis();
    private int c = -240;
}
