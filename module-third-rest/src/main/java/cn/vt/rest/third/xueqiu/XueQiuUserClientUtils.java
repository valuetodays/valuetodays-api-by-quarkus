package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.xueqiu.vo.XueQiuUserTimelineResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
public class XueQiuUserClientUtils {

    /**
     * 获取作者发布的文章.
     * 注意：不要频繁调用本接口，有限流
     *
     * @param url    url
     * @param userId userId
     * @param cookie cookie
     */
    public static XueQiuUserTimelineResp timeline(String url,
                                                  String userId,
                                                  String cookie) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Referer", "https://xueqiu.com/u/" + userId);
        headers.putAll(
            Map.of(
                "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) "
                    + "Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0",
                "Accept", "application/json, text/plain, */*",
                "Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8",
                "Host", "xueqiu.com",
                "Dnt", "1",
                "sec-ch-ua-platform", "\"Windows\""
            )
        );
        headers.put("Cookie", cookie);
        String s = HttpClient4Utils.doGet(url, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, XueQiuUserTimelineResp.class);
    }

    /**
     * 获取用户发布的文章.
     *
     * @param page   当前页面数
     * @param userId 用户id
     * @param cookie cookie，外部方法调用时不要使用带cookie的方法
     */
    public static XueQiuUserTimelineResp timeline(int page, String userId, String cookie) {
        String url = "https://xueqiu.com/v4/statuses/user_timeline.json";
        url += "?user_id=" + userId;
        url += "&type=0"; // 原发布
        if (page > 0) {
            url += "&page=" + page;
        }
        url += "&_=" + System.currentTimeMillis();
        return timeline(url, userId, cookie);
    }
}
