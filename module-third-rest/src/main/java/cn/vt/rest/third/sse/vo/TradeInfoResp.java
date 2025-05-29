package cn.vt.rest.third.sse.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeInfoResp extends BaseResp<TradeInfoResp.Item> {

    @Data
    public static class Item implements Serializable {
        @JsonAlias({"TO_RATE"})
        private BigDecimal huanShouPtg; // 流通股换手率 "6.91"
        @JsonAlias({"TRADE_AMT"})
        private BigDecimal tradeAmountInWan; // 成交金额（万元） "107379.91"
        @JsonAlias({"TRADE_VOL"})
        private BigDecimal tradeVolumeInWan; //  成交量（万份） "65652.21"
        @JsonAlias({"TX_DATE"})
        private String tradeDate; //  20250102
        @JsonAlias({"OPEN_PRICE"})
        private String openPrice; //  开盘价  "1.625"
        @JsonAlias({"CLOSE_PRICE"})
        private String closePrice; //  收盘价  "1.625"
        @JsonAlias({"HIGH_PRICE"})
        private String highPrice; //  最高价  "1.64" todo: 不准确
        @JsonAlias({"LOW_PRICE"})
        private String lowPrice; //  最低价  "1.62" todo: 不准确
    }
}
