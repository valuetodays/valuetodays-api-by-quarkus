package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @see EastMoneyStockDetailData
 */
@Data
public class EastMoneyStockDetailDataTyped implements Serializable {
    @JsonProperty("f11")
    private BigDecimal buy5Price; // 买5价
    @JsonProperty("f12")
    private int buy5Volume; // 买5量
    @JsonProperty("f13")
    private BigDecimal buy4Price; // 买4价
    @JsonProperty("f14")
    private int buy4Volume; // 买4量
    @JsonProperty("f15")
    private BigDecimal buy3Price; // 买3价
    @JsonProperty("f16")
    private int buy3Volume; // 买3量
    @JsonProperty("f17")
    private BigDecimal buy2Price; // 买2价
    @JsonProperty("f18")
    private int buy2Volume; // 买2量
    @JsonProperty("f19")
    private BigDecimal buy1Price; // 买1价
    @JsonProperty("f20")
    private int buy1Volume; // 买1量
    @JsonProperty("f31")
    private BigDecimal sell5Price; // 卖5价
    @JsonProperty("f32")
    private int sell5Volume; // 卖5量
    @JsonProperty("f33")
    private BigDecimal sell4Price; // 卖4价
    @JsonProperty("f34")
    private int sell4Volume; // 卖4量
    @JsonProperty("f35")
    private BigDecimal sell3Price; // 卖3价
    @JsonProperty("f36")
    private int sell3Volume; // 卖3量
    @JsonProperty("f37")
    private BigDecimal sell2Price; // 卖2价
    @JsonProperty("f38")
    private int sell2Volume; // 卖2量
    @JsonProperty("f39")
    private BigDecimal sell1Price; // 卖1价
    @JsonProperty("f40")
    private int sell1Volume; // 卖1量

    @JsonProperty("f43")
    private BigDecimal price; // 现价
    @JsonProperty("f44")
    private BigDecimal highestPrice; // 最高价
    @JsonProperty("f45")
    private BigDecimal lowestPrice; // 最低价
    @JsonProperty("f46")
    private BigDecimal openPrice; // 最低价
    @JsonProperty("f47")
    private BigDecimal tradeVolumeInShou; // 成交量 （手）
    @JsonProperty("f48")
    private BigDecimal tradeAmount; // 成交额
    @JsonProperty("f49")
    private BigDecimal outPan; // 外盘
    @JsonProperty("f50")
    private BigDecimal liangbi; // 量比
    @JsonProperty("f51")
    private BigDecimal upStop; // 涨停
    @JsonProperty("f52")
    private BigDecimal downStop; // 跌停

    @JsonProperty("f57")
    private String code;
    @JsonProperty("f58")
    private String name;
    @JsonProperty("f60")
    private BigDecimal lastClose; //
    @JsonProperty("f71")
    private BigDecimal avgPrice;  //
    @JsonProperty("f86")
    private LocalDateTime statDateTime;  // 统计时间

    @JsonProperty("f161")
    private BigDecimal inPan;  // 内盘
    @JsonProperty("f168")
    private BigDecimal huanShouPtg;  // 换手
    @JsonProperty("f169")
    private BigDecimal changeOffsetPrice;  // 涨跌差价=(当前价-昨收)
    @JsonProperty("f170")
    private BigDecimal changeOffsetPricePtg;  // 涨跌百分比=(当前价-昨收)/昨收
    @JsonProperty("f171")
    private BigDecimal zhenFuPtg;  // 振幅
    @JsonProperty("f191")
    private BigDecimal weiBiPtg;  // 委比   数值是-2737，即是 -27.37%
    @JsonProperty("f192")
    private int weiCha;  // 委差：-8139
    @JsonProperty("f452")
    private int xianShou;  // 现手


    public StockDetailVO toStockDetailVO() {
        StockDetailVO vo = new StockDetailVO();
        vo.setCode(getCode());
        vo.setName(getName());
        vo.setPrice((getPrice()));
        vo.setAvgPrice((getAvgPrice()));
        vo.setWaiPan((getOutPan()));
        vo.setNeiPan(getInPan());
        return vo;
    }


}
