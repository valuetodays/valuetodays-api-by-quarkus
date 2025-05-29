package cn.vt.rest.third.sse.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundListResp extends BaseResp<FundListResp.FundListItem> {

    @Data
    public static class FundListItem implements Serializable {
        @JsonAlias({"HSCEI"})
        private String indexCode; // "HSCEI"
        @JsonAlias({"INDEX_NAME"})
        private String indexName; // "恒生中国企业指数"
        @JsonAlias({"fundCode"})
        private String fundCode; //  "510900"
        @JsonAlias({"fundAbbr"})
        private String fundAbbr; //  "H股ETF"
        @JsonAlias({"listingDate"})
        private String listingDate; // "20121022"
    }
}
