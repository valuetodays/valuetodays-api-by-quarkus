package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-23
 */
@Data
public class XueQiuMinuteChartData implements Serializable {
    private double last_close;
    private int items_size;
    private List<Item> items;

    @Data
    public static class Item implements Serializable {
        // 当前股价
        private Double current;
        // 成交量
        private double volume;
        // 平均价
        private Double avg_price;
        // 涨跌额
        private double chg;
        // 涨跌百分比
        private double percent;
        // 时间戳（分钟级别）
        private long timestamp;

    }

}
