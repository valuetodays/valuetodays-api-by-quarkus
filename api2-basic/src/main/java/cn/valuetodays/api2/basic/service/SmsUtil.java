package cn.valuetodays.api2.basic.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class SmsUtil {
    /**
     * @param mobile  手机号码
     * @param content 短信内容
     */
    public static SmsResult sendSms(String mobile, String content) {
        SmsResult smsResult = SmsResult.success(content);
        log.debug("sms-result: {}", smsResult);
        return smsResult;
    }

    /**
     * 往指定手机号上发送短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码内容
     */
    public static SmsResult sendSmscode(String mobile, String code) {
        return sendSms(mobile, "验证码：" + code);
    }

    @Getter
    public static class SmsResult implements Serializable {
        private static final String SUCCESS = "发送成功";
        private final boolean success;
        private final String result;

        private SmsResult(boolean success, String result) {
            this.success = success;
            this.result = result;
        }

        public static SmsResult success(String smsContent) {
            return new SmsResult(true, smsContent);
        }

        public static SmsResult fail(String reason) {
            return new SmsResult(false, reason);
        }

    }

}
