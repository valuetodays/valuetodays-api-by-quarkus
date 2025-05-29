package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-15
 */
@Data
public class XueQiuQuoteDetailData implements Serializable {
    private Market market;
    private Quote quote;
    private Others others;
    private List<Tag> tags;

    @Data
    public static class Market implements Serializable {
        private int status_id;  // 8
        private String region; // "CN",
        private String status; // "休市",
        private String time_zone; // "Asia/Shanghai",
        private String time_zone_desc; // null
        private int delay_tag; // "Asia/Shanghai",
    }

    @Data
    public static class Quote implements Serializable {
        private String symbol; // "SH513300",
        private String code; // "SH513300",
        private BigDecimal acc_unit_nav; // 1.716,
        private BigDecimal high52w; // 2.055,
        private Long nav_date; // 1725984000000
        private BigDecimal avg_price; //  1.834,
        private int delayed; // 0
        private int type; // 13,
        private Long expiration_date; // null,
        private BigDecimal percent; // 1.43,
        private BigDecimal tick_size; // 0.001,
        private BigDecimal float_shares; //  null,
        private BigDecimal limit_down; //   1.632,
        private BigDecimal amplitude; //    0.88,
        private BigDecimal current; //   1.839,
        private BigDecimal high; //   1.843,
        private BigDecimal current_year_percent; //   22.03,
        private BigDecimal float_market_capital; //  null,
        private Long issue_date; //  1604505600000,
        private BigDecimal low; //  1.827,
        private String sub_type; // "EBS",
        private BigDecimal market_capital; // 4613445969
        private String currency; // "CNY",
        private int lot_size; // lot_size
        private String lock_set; // null,
        private BigDecimal iopv; // 1.7347,
        private BigDecimal premium_rate; //  6.01, 	溢价率%
        private Long timestamp; // 1726210800000
        private Long found_date; // 1603296000000
        private BigDecimal amount; // 485177348
        private BigDecimal chg; //  0.026,
        private BigDecimal last_close; // 1.813,
        private BigDecimal volume; // 264515600
        private BigDecimal volume_ratio; // null,
        private BigDecimal limit_up; // 1.994,
        private BigDecimal turnover_rate; // null,
        private BigDecimal low52w; // 0.964,
        private String name; // "纳斯达克ETF",
        private String exchange; // "SH",
        private BigDecimal unit_nav; // 1.716,
        private Long time; // 1726210800000
        private Long total_shares; // 2508671000
        private BigDecimal open; //  1.831,
        private int status; //  1
    }

    @Data
    public static class Others implements Serializable {
        private BigDecimal pankou_ratio;
        private boolean cyb_switch;
    }

    @Data
    public static class Tag implements Serializable {
        private String description;
        private int value;
    }
}
