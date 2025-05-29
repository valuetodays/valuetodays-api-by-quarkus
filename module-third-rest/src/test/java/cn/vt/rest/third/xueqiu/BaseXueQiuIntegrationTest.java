package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.quote.CookieClientUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-10-01
 */
public abstract class BaseXueQiuIntegrationTest {
    protected String getToken() {
        return CookieClientUtils.pullXueqiuCookies();
    }

}
