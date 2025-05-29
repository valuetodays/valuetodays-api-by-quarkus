package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@Data
public class PushCookieReq implements Serializable {
    public static final String DOMAIN_XUEQIU = "xueqiu.com";
    public static final String DOMAIN_WEIBO = "weibo.com";
    private List<String> cookieTextArr;
    private String domain;
    private String type;
    private String referer;
}
