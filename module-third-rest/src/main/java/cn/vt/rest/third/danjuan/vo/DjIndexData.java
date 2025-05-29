package cn.vt.rest.third.danjuan.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-04 17:09
 */
@Data
public class DjIndexData implements Serializable, DjBaseData {
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("size")
    private int size;
    @JsonProperty("total_items")
    private int totalItems;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("items")
    private List<Item> items;

    @Data
    public static class Item implements Serializable {
        @JsonProperty("index_code")
        private String indexCode;
        private String name;
    }
}
