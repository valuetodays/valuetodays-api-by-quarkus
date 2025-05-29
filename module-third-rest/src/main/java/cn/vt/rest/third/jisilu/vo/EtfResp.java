package cn.vt.rest.third.jisilu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @see <a href="https://www.jisilu.cn/data/qdii/#qdiie">qdii</a>
 * @since 2025-01-02
 */
@Data
public class EtfResp implements Serializable {
    private int page;
    private List<Row> rows;

    @Data
    public static class Row implements Serializable {
        private String id;
        private Cell cell;
    }

    @Data
    public static class Cell implements Serializable {
        private String fund_id; // "513300"
        private String fund_nm; // "纳斯达克ETF"
        private String qtype;  // E
        private String urls;  // "http://www.chinaamc.com/fund/513300/index.shtml",
        private String t0;  // "Y",
        private BigDecimal price;  // "2.105",
        private String price_dt;  // "2025-01-02",
        private String increase_rt;  // 价格涨跌： "0.53%",
        private BigDecimal volume;  // 成交额(万元) "54597.51",
        private BigDecimal amount;  // 最新份额(万份)
        private String last_time;  // "14:59:59",
        private String discount_rt;  // 溢价率 "9.62%",
        private String index_nm;  // "纳斯达克100",
        private BigDecimal m_fee;  // 管理费率 "0.60",
        private BigDecimal t_fee;  // 托管费率 "0.20"
        private BigDecimal mt_fee;  // 管托费(管理费+托管费) "0.80"
        private String mt_fee_tips;  // "管理费：0.60%\n托管费：0.20%",
        private String money_cd;  // "USD",
        private String turnover_rt;  // 换手率 "9.80%",
    }

}
