package cn.vt.rest.third.xueqiu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-04-27
 */
@Data
public class XueQiuUserTimelineResp implements Serializable {
    private int count;
    private int maxPage;
    private int page;
    private int total;
    private List<StatusItem> statuses;


    @Data
    public static class StatusItem implements Serializable {

        private Long id;
        @JsonProperty("user_id")
        private Long userId;
        private String source;
        @JsonProperty("created_at")
        private Long createdAt; // 毫秒
        @JsonProperty("fav_count")
        private Integer favCount;
        private String target; //  "/5337932916/287793945" url
        private String text;
        private String type; // 3:专栏，0:帖子
    }
}

