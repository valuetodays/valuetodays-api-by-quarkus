package cn.valuetodays.api2.module.fortune.channel;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-09-14
 */
@Data
public class StockHoldersInfo implements Serializable {
    // yyyyMMdd
    private Integer statDate;
    // 513310
    private String secCode;
    // 300ETF
    private String secAbbr;
    // 1100
    private Integer holdVolume;
    // 可以为空
    private BigDecimal costPrice;
    // 可以为空
    private BigDecimal marketPrice;

    public StockHoldersInfo() {
    }

    @JsonIgnore
    public void initValues() {

    }
}
