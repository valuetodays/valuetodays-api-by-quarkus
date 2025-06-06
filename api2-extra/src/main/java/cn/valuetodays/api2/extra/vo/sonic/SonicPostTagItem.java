package cn.valuetodays.api2.extra.vo.sonic;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@Data
public class SonicPostTagItem implements Serializable {
    private Long id;
    private String name;
    private String slug;
    private String color; //    "color": "#cfd3d7"
}
