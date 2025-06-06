package cn.valuetodays.api2.extra.vo.sonic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SonicPagedPostsResp extends SonicBaseResp<SonicPagedPostsResp.PagedPostsRespData> {

    @Data
    public static class PagedPostsRespData implements Serializable {
        private List<SonicPostItem> content;
        private int pages;
        private int total;
        private int rpp;
        @JsonProperty("pageNum")
        private int pageNum;
        @JsonProperty("hasNext")
        private boolean hasNext;
        @JsonProperty("hasPrevious")
        private boolean hasPrevious;
        @JsonProperty("hasContent")
        private boolean hasContent;
    }
}
