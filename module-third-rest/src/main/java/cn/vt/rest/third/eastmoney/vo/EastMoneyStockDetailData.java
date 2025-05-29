package cn.vt.rest.third.eastmoney.vo;

import cn.vt.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static cn.vt.rest.third.eastmoney.EastMoneyCommons.parseAsBigDecimal;
import static cn.vt.rest.third.eastmoney.EastMoneyCommons.parseAsBigDecimalDivide100;
import static cn.vt.rest.third.eastmoney.EastMoneyCommons.parseAsBigDecimalDivide1000;

/**
 * .
 *
 * @author lei.liu
 * @see EastMoneyStockDetailDataTyped
 * @since 2025-01-04
 */
@Data
public class EastMoneyStockDetailData implements Serializable {
    @JsonProperty("f11")
    private String buy5Price; // 买5价，需要除以1000
    @JsonProperty("f12")
    private String buy5Volume; // 买5量
    @JsonProperty("f13")
    private String buy4Price; // 买4价，需要除以1000
    @JsonProperty("f14")
    private String buy4Volume; // 买4量
    @JsonProperty("f15")
    private String buy3Price; // 买3价，需要除以1000
    @JsonProperty("f16")
    private String buy3Volume; // 买3量
    @JsonProperty("f17")
    private String buy2Price; // 买2价，需要除以1000
    @JsonProperty("f18")
    private String buy2Volume; // 买2量
    @JsonProperty("f19")
    private String buy1Price; // 买1价，需要除以1000
    @JsonProperty("f20")
    private String buy1Volume; // 买1量
    @JsonProperty("f31")
    private String sell5Price; // 卖5价，需要除以1000
    @JsonProperty("f32")
    private String sell5Volume; // 卖5量
    @JsonProperty("f33")
    private String sell4Price; // 卖4价，需要除以1000
    @JsonProperty("f34")
    private String sell4Volume; // 卖4量
    @JsonProperty("f35")
    private String sell3Price; // 卖3价，需要除以1000
    @JsonProperty("f36")
    private String sell3Volume; // 卖3量
    @JsonProperty("f37")
    private String sell2Price; // 卖2价，需要除以1000
    @JsonProperty("f38")
    private String sell2Volume; // 卖2量
    @JsonProperty("f39")
    private String sell1Price; // 卖1价，需要除以1000
    @JsonProperty("f40")
    private String sell1Volume; // 卖1量

    @JsonProperty("f43")
    private String price; // 现价，需要除以1000
    @JsonProperty("f44")
    private String highestPrice; // 最高价，需要除以1000
    @JsonProperty("f45")
    private String lowestPrice; // 最低价，需要除以1000
    @JsonProperty("f46")
    private String openPrice; // 最低价，需要除以1000
    @JsonProperty("f47")
    private String tradeVolume; // 成交量
    @JsonProperty("f48")
    private String tradeAmount; // 成交额
    @JsonProperty("f49")
    private String outPan; // 外盘
    @JsonProperty("f50")
    private String liangbi; // 量比，需要除以100
    @JsonProperty("f51")
    private String upStop; // 涨停，需要除以1000
    @JsonProperty("f52")
    private String downStop; // 跌停，需要除以1000

    @JsonProperty("f57")
    private String code;
    @JsonProperty("f58")
    private String name;
    @JsonProperty("f60")
    private String lastClose; // 需要除以1000
    @JsonProperty("f71")
    private String avgPrice;  //  需要除以1000
    @JsonProperty("f86")
    private Long statDateTimeInSecond;  // 统计时间

    @JsonProperty("f161")
    private String inPan;  // 内盘
    @JsonProperty("f168")
    private String huanShouPtg;  // 换手，需要除以100
    @JsonProperty("f169")
    private String changeOffsetPrice;  // 涨跌差价=(当前价-昨收)，需要除以1000
    @JsonProperty("f170")
    private String changeOffsetPricePtg;  // 涨跌百分比=(当前价-昨收)/昨收，需要除以100
    @JsonProperty("f171")
    private String zhenFuPtg;  // 振幅，需要除以100
    @JsonProperty("f191")
    private String weiBiPtg;  // 委比，需要除以100   数值是-2737，即是 -27.37%
    @JsonProperty("f192")
    private String weiCha;  // 委差：-8139
    @JsonProperty("f452")
    private String xianShou;  // 现手

    private static int parseAsInt(String str) {
        if (StringUtils.equalsAny(str, null, "", "-")) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    @JsonIgnore
    public EastMoneyStockDetailDataTyped toTyped() {
        EastMoneyStockDetailDataTyped typed = new EastMoneyStockDetailDataTyped();
        typed.setBuy5Price(parseAsBigDecimalDivide1000(buy5Price));
        typed.setBuy5Volume(parseAsInt(buy5Volume));
        typed.setBuy4Price(parseAsBigDecimalDivide1000(buy4Price));
        typed.setBuy4Volume(parseAsInt(buy4Volume));
        typed.setBuy3Price(parseAsBigDecimalDivide1000(buy3Price));
        typed.setBuy3Volume(parseAsInt(buy3Volume));
        typed.setBuy2Price(parseAsBigDecimalDivide1000(buy2Price));
        typed.setBuy2Volume(parseAsInt(buy2Volume));
        typed.setBuy1Price(parseAsBigDecimalDivide1000(buy1Price));
        typed.setBuy1Volume(parseAsInt(buy1Volume));
        typed.setSell5Price(parseAsBigDecimalDivide1000(sell5Price));
        typed.setSell5Volume(parseAsInt(sell5Volume));
        typed.setSell4Price(parseAsBigDecimalDivide1000(sell4Price));
        typed.setSell4Volume(parseAsInt(sell4Volume));
        typed.setSell3Price(parseAsBigDecimalDivide1000(sell3Price));
        typed.setSell3Volume(parseAsInt(sell3Volume));
        typed.setSell2Price(parseAsBigDecimalDivide1000(sell2Price));
        typed.setSell2Volume(parseAsInt(sell2Volume));
        typed.setSell1Price(parseAsBigDecimalDivide1000(sell1Price));
        typed.setSell1Volume(parseAsInt(sell1Volume));

        typed.setCode(code);
        typed.setName(name);

        typed.setPrice(parseAsBigDecimalDivide1000(price));
        typed.setAvgPrice(parseAsBigDecimalDivide1000(avgPrice));
        typed.setOutPan(parseAsBigDecimal(outPan));
        typed.setInPan(parseAsBigDecimal(inPan));
        typed.setTradeVolumeInShou(parseAsBigDecimal(tradeVolume));
        typed.setTradeAmount(parseAsBigDecimal(tradeAmount));

        typed.setHighestPrice(parseAsBigDecimalDivide1000(highestPrice));
        typed.setLowestPrice(parseAsBigDecimalDivide1000(lowestPrice));
        typed.setOpenPrice(parseAsBigDecimalDivide1000(openPrice));
        typed.setLiangbi(parseAsBigDecimalDivide100(liangbi));
        typed.setUpStop(parseAsBigDecimalDivide1000(upStop));
        typed.setDownStop(parseAsBigDecimalDivide1000(downStop));
        typed.setLastClose(parseAsBigDecimalDivide1000(lastClose));
        typed.setStatDateTime(DateUtils.getDate(statDateTimeInSecond * 1000L));
        typed.setHuanShouPtg(parseAsBigDecimalDivide100(huanShouPtg));
        typed.setChangeOffsetPrice(parseAsBigDecimalDivide1000(changeOffsetPrice));
        typed.setChangeOffsetPricePtg(parseAsBigDecimalDivide100(changeOffsetPricePtg));
        typed.setZhenFuPtg(parseAsBigDecimalDivide100(zhenFuPtg));
        typed.setWeiBiPtg(parseAsBigDecimalDivide100(weiBiPtg));
        typed.setWeiCha(parseAsInt(weiCha));
        typed.setXianShou(parseAsInt(xianShou));
        return typed;
    }

}
