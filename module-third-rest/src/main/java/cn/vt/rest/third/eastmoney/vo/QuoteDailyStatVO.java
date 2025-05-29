package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * 指数每日统计
 *
 * @author lei.liu
 * @since 2023-04-02 13:02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuoteDailyStatVO implements Serializable {


    public static final Function<String, QuoteDailyStatVO> TRANSFORMER = str -> {
        String[] dataArr = StringUtils.split(str, ",");
        String dateStr = dataArr[0]; // yyyy-MM-dd
        String openPxStr = dataArr[1];  // 开盘点
        String closePxStr = dataArr[2];  // 收盘点
        String highPxStr = dataArr[3];  // 最高点
        String lowPxStr = dataArr[4];  // 最低点
        String busiAmountStr = dataArr[5];  // 成交量
        String busiMoneyStr = dataArr[6];  // 成交额
        String zhenfuStr = dataArr[7];  // 振幅
        String offsetPxPercentageStr = dataArr[8];  // 涨跌幅
        String offsetPxStr = dataArr[9];  // 涨跌额
        String huanshoulvStr = dataArr[10];  // 换手率
        QuoteDailyStatVO idsp = new QuoteDailyStatVO();
        idsp.setStatDate(Integer.parseInt(StringUtils.replace(dateStr, "-", "")));
        idsp.setClosePx(Double.parseDouble(closePxStr));
        idsp.setHighPx(Double.parseDouble(highPxStr));
        idsp.setLowPx(Double.parseDouble(lowPxStr));
        idsp.setOpenPx(Double.parseDouble(openPxStr));
        idsp.setBusiAmount(new BigDecimal(busiAmountStr).longValue());
        idsp.setBusiMoney(new BigDecimal(busiMoneyStr).longValue());
        idsp.setOffsetPx(Double.parseDouble(offsetPxStr));
        idsp.setOffsetPxPercentage(Double.parseDouble(offsetPxPercentageStr));
        idsp.setZhenfuPercentage(Double.parseDouble(zhenfuStr));
        idsp.setHuanshoulvPercentage(Double.parseDouble(huanshoulvStr));
        return idsp;
    };

    private Integer statDate;
    private String code;
    private String region;
    private Double closePx;
    private Double highPx;
    private Double lowPx;
    private Double openPx;
    private Long busiAmount;
    private Long busiMoney;
    private Double offsetPx;
    private Double offsetPxPercentage;
    private Double zhenfuPercentage;
    private Double huanshoulvPercentage;

}
