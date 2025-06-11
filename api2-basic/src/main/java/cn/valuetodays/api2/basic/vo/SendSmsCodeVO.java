package cn.valuetodays.api2.basic.vo;

import java.io.Serializable;

/**
 * @author liulei
 * @since 2017-11-14 17:12
 */
public class SendSmsCodeVO implements Serializable {
    private String code;
    private long createAt;

    public SendSmsCodeVO() {
    }

    public SendSmsCodeVO(String code) {
        this.code = code;
        this.createAt = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    // set方法只让fastjson使用
    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
}
