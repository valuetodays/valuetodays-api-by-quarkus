package cn.valuetodays.api2.client.req;

import cn.vt.core.Title;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-19
 */
@Data
public class XueqiuStockMinuteChartReq implements Serializable {
    @Title(value = "代码和市场编号", example = "SH513300")
    private String codeWithRegion;
    private String period;
}
