package cn.valuetodays.api2.basic;

/**
 * @author liulei
 * @since 2017-05-10 10:35
 */
public class RedisKeyConstant {
    /**
     * func purviews
     */
    public static final String COMMON_FUNC_PURVIEWS = "common.func.purviews";
    /**
     * 登录用户
     */
    public static final String LOGINED_USER = "logined.user.";
    /**
     * 手机验证码
     */
    public static final String MOBILE_SMSCODE = "mobile.smscode.";
    /**
     * 短信验证码
     */
    public static final String AUTHCODE_KEY = "authcode.key.";
    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = "reset.password.";
    /**
     * token
     */
    public static final String TOKEN = "token";
    /**
     * md5id
     */
    public static final String MD5ID = "md5id";
    private RedisKeyConstant() {
    }

}

