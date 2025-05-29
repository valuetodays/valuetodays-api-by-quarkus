package cn.vt.rest.third.xueqiu;

import cn.vt.exception.CommonException;
import cn.vt.rest.third.xueqiu.vo.CommentArticleData;
import cn.vt.rest.third.xueqiu.vo.QueryTokenData;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-06-16
 */
public class XueQiuArticleClientUtils {
    public static QueryTokenData querySessionToken(long timestamp, String referer, String cookie) {
        Map<String, String> headers = buildHeaders(cookie, referer);
        String url = "https://xueqiu.com/provider/session/token.json?api_path=%2Fstatuses%2Freply.json&_={timestamp}";
        String replacedUrl = StringUtils.replace(url, "{timestamp}", String.valueOf(timestamp));
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, QueryTokenData.class);
    }

    public static QueryTokenData querySessionToken(String referer, String cookie) {
        return querySessionToken(System.currentTimeMillis(), referer, cookie);
    }

    private static Map<String, String> buildHeaders(String nullableCookie, String referer) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("origin", "https://xueqiu.com");
        if (StringUtils.isNotBlank(referer)) {
            headers.put("Referer", referer);
        } else {
            headers.put("Referer", "https://xueqiu.com");
        }
        if (StringUtils.isNotBlank(nullableCookie)) {
            headers.put("cookie", nullableCookie);
        }

        headers.putAll(
            Map.of(
                "authority", "stock.xueqiu.com",
                "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) "
                    + "Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0",
                "Accept", "application/json, text/plain, */*",
                "Accept-Language", "ezh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
                "Dnt", "1",
                "sec-ch-ua-platform", "\"Windows\"",
                "sec-gpc", "1",
                "sec-ch-ua-mobile", "?0",
                "X-Requested-With", "XMLHttpRequest"
            )
        );
        return headers;
    }

    public static CommentArticleData commentArticle(String referer, Map<String, String> requestBody, String cookie) {
        Map<String, String> headers = buildHeaders(cookie, referer);
        String url = "https://xueqiu.com/statuses/reply.json";
        String s = HttpClient4Utils.doPost(url, requestBody, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, CommentArticleData.class);
    }

    public static CommentArticleData commentArticle(String referer, String comment, String cookie) {
        String articleId = StringUtils.substringAfterLast(referer, "/");
        QueryTokenData data = querySessionToken(referer, cookie);
        if (Objects.isNull(data)) {
            throw new CommonException("no session token object");
        }
        String sessionToken = data.getSession_token();
        if (Objects.isNull(sessionToken)) {
            throw new CommonException("no session token value");
        }
        Map<String, String> body = new HashMap<>();
        body.put("forward", "0");
        body.put("id", String.valueOf(articleId));
        body.put("comment", "<p>" + comment + "</p>");
        body.put("post_source", "");
        body.put("post_position", "pc_status_post");
        body.put("session_token", sessionToken);
        return commentArticle(referer, body, cookie);
    }

    public static CommentArticleData commentArticleQuick(String referer, String cookie) {
        return commentArticle(referer, "1", cookie);
    }

}
