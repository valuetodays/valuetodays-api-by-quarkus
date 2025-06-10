package cn.valuetodays.api2.basic.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-27 16:46
 */
@Data
public class LeftDaysInLifeReq implements Serializable {
    // 用户姓名
    private String userName = "无名";
    // yyyy-MM-dd，出生日
    @NotBlank(message = "出生日期不可为空，yyyy-MM-dd")
    private String dayOfBirth;
    // 期望寿命
    private int expectedAge = 65;
}
