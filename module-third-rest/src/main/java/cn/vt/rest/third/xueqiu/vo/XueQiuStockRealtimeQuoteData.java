package cn.vt.rest.third.xueqiu.vo;

import cn.vt.core.Title;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-23 10:00
 */
@Title("雪球股票实时报价-data对象")
@Data
public class XueQiuStockRealtimeQuoteData implements Serializable {
    @Title(value = "股票代号", example = "SH510310")
    private String symbol;
    @Title(value = "当前股价", example = "1.888")
    private Double current;
    @Title(value = "涨跌百分比，不需要再除以100", example = "-0.21")
    private Double percent;
    @Title(value = "涨跌额", example = "-0.004")
    private Double chg;
    @Title(value = "当前时间戳", example = "1684806115110")
    private Long timestamp;
    @Title(value = "成交量", example = "6148200")
    private Long volume;
    @Title(value = "成交额", example = "1.162033E7")
    private Double amount;
    @Title(value = "总市值", example = "1.62751403712E10")
    @JsonProperty("market_capital")
    private Double marketCapital;
    @Title(value = "？", example = "")
    @JsonProperty("float_market_capital")
    private String floatMarketCapital;
    @Title(value = "换手率", example = "")
    @JsonProperty("turnover_rate")
    private String turnoverRate;
    @Title(value = "振幅，不需要再除以100", example = "0.37")
    private Double amplitude;
    @Title(value = "开盘价", example = "1.892")
    private Double open;
    @Title(value = "昨收价", example = "1.892")
    @JsonProperty("last_close")
    private Double lastClose;
    @Title(value = "最高价", example = "1.894")
    private Double high;
    @Title(value = "最低价", example = "1.887")
    private Double low;
    @Title(value = "平均价", example = "1.89")
    @JsonProperty("avg_price")
    private Double avgPrice;
    @Title(value = "trade_volume", example = "30000")
    @JsonProperty("trade_volume")
    private Long tradeVolume;
    @Title(value = "side", example = "1")
    private Integer side;
    @Title(value = "是否可交易", example = "true")
    @JsonProperty("is_trade")
    private Boolean isTrade;
    @Title(value = "level", example = "1")
    private Integer level;
    @Title(value = "trade_session", example = "")
    @JsonProperty("trade_session")
    private String tradeSession;
    @Title(value = "trade_type", example = "")
    @JsonProperty("trade_type")
    private String tradeType;
    @Title(value = "current_year_percent", example = "2.33")
    @JsonProperty("current_year_percent")
    private Double currentYearPercent;
    @Title(value = "trade_unique_id", example = "6148200")
    @JsonProperty("trade_unique_id")
    private String tradeUniqueId;
    @Title(value = "type", example = "13")
    private Integer type;
    @Title(value = "bid_appl_seq_num", example = "")
    @JsonProperty("bid_appl_seq_num")
    private String bidApplSeqNum;
    @Title(value = "offer_appl_seq_num", example = "")
    @JsonProperty("offer_appl_seq_num")
    private String offerApplSeqNum;
    @Title(value = "volume_ext", example = "")
    @JsonProperty("volume_ext")
    private String volumeExt;
    @Title(value = "traded_amount_ext", example = "")
    @JsonProperty("traded_amount_ext")
    private String tradedAmountExt;
    @Title(value = "trade_type_v2", example = "")
    @JsonProperty("trade_type_v2")
    private String tradeTypeV2;

}

