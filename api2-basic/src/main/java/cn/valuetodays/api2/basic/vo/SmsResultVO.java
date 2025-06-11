package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liulei
 * @since 2017-11-24 10:03
 */
@Data
public class SmsResultVO implements Serializable {
    private String msg;
    private String smsToken; // 使用场景：重置密码、更换手机号;
    private int code; // 0不正常，1正常

    public static SmsResultVO success(String token) {
        SmsResultVO s = new SmsResultVO();
        s.setCode(1);
        s.setSmsToken(token);
        return s;
    }

    public static SmsResultVO fail(String msg) {
        SmsResultVO s = new SmsResultVO();
        s.setCode(0);
        s.setMsg(msg);
        return s;
    }

}
