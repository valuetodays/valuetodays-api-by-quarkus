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
 * @since 2025-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EtfDailyVolumeResp extends BaseResp<EtfDailyVolumeResp.Item> {

    @Data
    public static class Item implements Serializable {
        @JsonAlias({"ETF_TYPE"})
        private String etfType; // "跨市"
        @JsonAlias({"NUM"})
        private String num; // "80"
        @JsonAlias({"SEC_CODE"})
        private String code; // "512280"
        @JsonAlias({"STAT_DATE"})
        private String statDateStr; // "2024-01-03"
        @JsonAlias({"TOT_VOL"})
        private BigDecimal totVolume; // 成交量（万份） "4200.57"
    }
}
