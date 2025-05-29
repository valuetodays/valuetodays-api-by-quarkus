package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-21
 */
@Data
public class IopvData implements Serializable {
    @JsonProperty("Feature")
    private String feature; // "055,317,318,350"
    @JsonProperty("LSJZList")
    private List<IopvItem> list;

    @Data
    public static class IopvItem implements Serializable {
        @JsonProperty("DWJZ")
        private BigDecimal dwjz; // 单位净值 "1.9297"
        @JsonProperty("FSRQ")
        private String dateStr; // 净值日期 "2024-12-19"
    }

}
