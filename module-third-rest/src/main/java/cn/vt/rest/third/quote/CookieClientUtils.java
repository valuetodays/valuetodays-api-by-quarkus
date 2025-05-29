package cn.vt.rest.third.quote;

import cn.vt.rest.third.xueqiu.vo.PushCookieReq;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.collections4.MapUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-10-01
 */
public class CookieClientUtils {
    public static Map<String, Object> pullCookie(Map<String, String> req) {
        String url = "http://quote.valuetodays.cn/cookie/anon/pull.do";
        Map<String, String> headers = Map.of("Content-Type", "application/json;charset=utf-8");
        String s = HttpClient4Utils.doPostJson(url, req, StandardCharsets.UTF_8.name(), headers);
        return JsonUtils.fromJson(s);
    }

    public static String pullCookie(String domain) {
        Map<String, Object> e = pullCookie(Map.of("domain", domain));
        if (MapUtils.isEmpty(e)) {
            return null;
        }
        return String.valueOf(e.get("cookies"));
    }

    public static String pullXueqiuCookies() {
        return pullCookie(PushCookieReq.DOMAIN_XUEQIU);
    }

}
