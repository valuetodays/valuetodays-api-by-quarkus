package cn.valuetodays.api2.extra.vo.sonic;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@Data
public class SonicPostItem implements Serializable {
    private Long id;
    private String title;
    private String status; // "status": "PUBLISHED",
    private String slug;
    private String editorType;
    private String originalContent;
    private LocalDateTime createTime;
    private LocalDateTime editTime;
    private LocalDateTime updateTime;

    private List<SonicPostTagItem> tags;
}
