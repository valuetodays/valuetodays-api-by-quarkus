package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static cn.vt.rest.third.eastmoney.EastMoneyCommons.parseAsBigDecimalDivide100;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-17
 */
@Data
public class EastMoneyIndustryInfoData implements Serializable {
    private int total;
    private List<IndustryInfoItem> diff;

    @Data
    public static class IndustryInfoItemTyped implements Serializable {
        private BigDecimal price; // 最新价/最新点位
        private BigDecimal chgPtg; // 涨跌幅，2.69代码2.69%
        private BigDecimal chgValue; // 涨跌额
        private BigDecimal huanShouLv; // 换手率，4.50代码4.5%
        private String code; // 板块ID，BK0481
        private String title; // 板块名称，汽车零部件
        private Long totalCap; // 总市值(元)
        private Integer shangZhangJiaShu; // 上涨家数
        private Integer xiaDieJiaShu; // 下跌家数
        private String lingZhangStockTitle; // 领涨股票
        private String lingZhangStockCode; // 领涨股票
        private BigDecimal lingZhangChgPtg; // 涨跌幅，10.00代表10.00%
        private String lingDieStockTitle; // 领跌股票
        private String lingDieStockCode; // 领跌股票
        private BigDecimal lingDieChgPtg; // 涨跌幅，-10.00代表-10.00%
    }

    public static class IndustryInfoItem implements Serializable {
        @JsonProperty("f1")
        private Integer f1; // 固定值2，不知道代表什么
        @JsonProperty("f2")
        private Integer price; // 最新价/最新点位，需要除以100
        @JsonProperty("f3")
        private Integer chgPtg; // 涨跌幅，269代码2.69%
        @JsonProperty("f4")
        private Integer chgValue; // 涨跌额，需要除以100
        @JsonProperty("f8")
        private Integer huanShouLv; // 换手率，450代码4.5%
        @JsonProperty("f12")
        private String code; // 板块ID，BK0481
        @JsonProperty("f13")
        private String f13; //
        @JsonProperty("f14")
        private String title; // 板块名称，汽车零部件
        @JsonProperty("f20")
        private Long totalCap; // 总市值(元)
        @JsonProperty("f104")
        private Integer shangZhangJiaShu; // 上涨家数
        @JsonProperty("f105")
        private Integer xiaDieJiaShu; // 下跌家数
        @JsonProperty("f128")
        private String lingZhangStockTitle; // 领涨股票
        @JsonProperty("f140")
        private String lingZhangStockCode; // 领涨股票
        @JsonProperty("f136")
        private Integer lingZhangChgPtg; // 涨跌幅，1000代表10.00%
        @JsonProperty("f207")
        private String lingDieStockTitle; // 领跌股票
        @JsonProperty("f208")
        private String lingDieStockCode; // 领跌股票
        @JsonProperty("f222")
        private Integer lingDieChgPtg; // 涨跌幅，-1000代表-10.00%

        @JsonIgnore
        public IndustryInfoItemTyped toTyped() {
            IndustryInfoItemTyped t = new IndustryInfoItemTyped();
            t.setPrice(parseAsBigDecimalDivide100(String.valueOf(price)));
            t.setChgPtg(parseAsBigDecimalDivide100(String.valueOf(chgPtg)));
            t.setChgValue(parseAsBigDecimalDivide100(String.valueOf(chgValue)));
            t.setHuanShouLv(parseAsBigDecimalDivide100(String.valueOf(huanShouLv)));
            t.setCode(code);
            t.setTitle(title);
            t.setTotalCap(totalCap);
            t.setShangZhangJiaShu(shangZhangJiaShu);
            t.setXiaDieJiaShu(xiaDieJiaShu);
            t.setLingZhangStockTitle(lingZhangStockTitle);
            t.setLingZhangStockCode(lingZhangStockCode);
            t.setLingZhangChgPtg(parseAsBigDecimalDivide100(String.valueOf(lingZhangChgPtg)));
            t.setLingDieStockTitle(lingDieStockTitle);
            t.setLingDieStockCode(lingDieStockCode);
            t.setLingDieChgPtg(parseAsBigDecimalDivide100(String.valueOf(lingDieChgPtg)));
            return t;
        }
    }

}
