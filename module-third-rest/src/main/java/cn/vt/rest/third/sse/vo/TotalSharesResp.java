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
 * @since 2025-01-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalSharesResp extends BaseResp<TotalSharesResp.Item> {

    @Data
    public static class Item implements Serializable {
        @JsonAlias({"STAT_DATE"})
        private String statDate; // "2025-01-02"
        @JsonAlias({"TOT_VOL"})
        private BigDecimal totalVolumeInWan; //  "949911.06"
    }
}
