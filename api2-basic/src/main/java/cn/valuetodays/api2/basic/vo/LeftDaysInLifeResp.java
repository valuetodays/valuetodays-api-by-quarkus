package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-27 16:46
 */
@Data
public class LeftDaysInLifeResp implements Serializable {
    // 用户姓名
    private String userName;
    // yyyyMMdd，出生日
    private String dayOfBirth;
    private String today;
    // 期望寿命
    private int expectedAge;
    // 年龄
    private int age;
    // 已活天数
    private int hadDays;
    // 剩余天数
    private int leftDays;
    // 已活天数百分比
    private int hadDaysPctg;
}
