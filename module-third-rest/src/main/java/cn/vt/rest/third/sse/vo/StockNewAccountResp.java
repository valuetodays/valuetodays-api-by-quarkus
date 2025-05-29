package cn.vt.rest.third.sse.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StockNewAccountResp extends BaseResp<StockNewAccountResp.Item> {
    @Data
    public static class Item implements Serializable {
        @JsonAlias({"A_ACCT"})
        private BigDecimal aAccount; // A 股，单位：万户
        @JsonAlias({"B_ACCT"})
        private BigDecimal bAccount; // B 股，单位：万户
        @JsonAlias({"FUND_ACCT"})
        private BigDecimal fundAccount; // 基金，单位：万户
        @JsonAlias({"TOTAL"})
        private BigDecimal totalAccount; // 全部，单位：万户
        @JsonAlias({"TERM"})
        private String yearMonth; // 年月，yyyy.MM
    }
}
