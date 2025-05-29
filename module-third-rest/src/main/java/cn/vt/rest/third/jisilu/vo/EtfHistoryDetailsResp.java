package cn.vt.rest.third.jisilu.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-05
 */
@Data
public class EtfHistoryDetailsResp implements Serializable {
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
        private BigDecimal discount_rt;  // 溢价率 "9.62",
        private BigDecimal price;  // "2.105",
        private String price_dt;  // "2025-01-02",
        @JsonAlias({"amount"})
        private BigDecimal totalVolumeInWan;  // 最新份额(万份)
    }

}
