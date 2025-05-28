package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-11-20
 */
@Data
public class NotifyCdciReq implements Serializable {
    @NotBlank(message = "repo不能为空")
    private String repo;
    @NotBlank(message = "content不能为空")
    private String content;
}
