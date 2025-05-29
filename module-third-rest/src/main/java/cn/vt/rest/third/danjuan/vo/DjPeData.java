package cn.vt.rest.third.danjuan.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-04 17:18
 */
@Data
public class DjPeData implements Serializable, DjBaseData {
    @JsonProperty("horizontal_lines")
    private List<HorizontalLine> horizontalLines;
    @JsonProperty("index_eva_pe_growths")
    private List<IndexEvaPeGrowth> peList;

    @Data
    public static class HorizontalLine implements Serializable {
        @JsonProperty("line_color")
        private String lineColor;
        @JsonProperty("line_name")
        private String lineName;
        @JsonProperty("line_value")
        private double lineValue;
    }

    @Data
    public static class IndexEvaPeGrowth implements Serializable {
        private double pe;
        private long ts;
    }
}
