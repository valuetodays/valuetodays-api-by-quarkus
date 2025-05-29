package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-27
 */
@Data
public class StockDetailVO {
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal avgPrice;
    // 内盘
    private BigDecimal waiPan;
    // 外盘
    private BigDecimal neiPan;

    // 建议操作
    private String suggest;

    @JsonIgnore
    public void computeSuggest() {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        if (price.compareTo(avgPrice) > 0 && neiPan.compareTo(waiPan) > 0) {
            this.suggest = "sell";
        } else if (price.compareTo(avgPrice) < 0 && neiPan.compareTo(waiPan) < 0) {
            this.suggest = "buy";
        }
    }


}
