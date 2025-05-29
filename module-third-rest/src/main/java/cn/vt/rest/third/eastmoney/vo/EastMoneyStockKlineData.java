package cn.vt.rest.third.eastmoney.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-10 15:21
 */
@Data
public class EastMoneyStockKlineData implements Serializable {
    private String code; // 000001
    private int market;
    private String name;
    private int decimal;
    private long dktotal;
    private double preKPrice;
    private String[] klines;
}
