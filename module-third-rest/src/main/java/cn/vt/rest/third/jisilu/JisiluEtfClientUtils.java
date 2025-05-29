package cn.vt.rest.third.jisilu;

import cn.vt.rest.third.jisilu.vo.EtfHistoryDetailsResp;
import cn.vt.rest.third.jisilu.vo.EtfResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-02
 */
public class JisiluEtfClientUtils {
    private JisiluEtfClientUtils() {
    }

    /**
     * 欧美t0 etf
     *
     * @see <a href="https://www.jisilu.cn/data/qdii/#qdiie">页面地址</a>
     */
    public static EtfResp getEtfForEurope() {
        return getEtf("https://www.jisilu.cn/data/qdii/qdii_list/E");
    }

    /**
     * 商品t0 etf
     *
     * @see <a href="https://www.jisilu.cn/data/qdii/#qdiie">页面地址</a>
     */
    public static EtfResp getEtfForCommodity() {
        return getEtf("https://www.jisilu.cn/data/qdii/qdii_list/C");
    }

    /**
     * 亚洲t0 etf
     *
     * @see <a href="https://www.jisilu.cn/data/qdii/#qdiia">页面地址</a>
     */
    public static EtfResp getEtfForAsia() {
        return getEtf("https://www.jisilu.cn/data/qdii/qdii_list/A");
    }

    /**
     * 黄金t0 etf
     *
     * @see <a href="https://www.jisilu.cn/data/etf/#gold">页面地址</a>
     */
    public static EtfResp getEtfForGold() {
        return getEtf("https://www.jisilu.cn/data/etf/gold_list/");
    }

    private static EtfResp getEtf(String urlPath) {
        String url = urlPath + "?___jsl=LST___t={ts}&rp=22&page=1";
        String replacedUrl = StringUtils.replace(url, "{ts}", String.valueOf(System.currentTimeMillis()));
        Map<String, String> headers = getHeaderMap();
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, EtfResp.class);
    }

    private static Map<String, String> getHeaderMap() {
        String headersAsString = "accept:application/json, text/javascript, */*; q=0.01\n"
            + "accept-encoding:gzip, deflate, br, zstd\n"
            + "accept-language:en,zh-CN;q=0.9,zh;q=0.8\n"
            + "cookie:kbz_newcookie=1; kbzw__Session=38gc4ql2g3lj2qdol49o5qc5g4\n"
            + "dnt:1\n"
            + "priority:u=1, i\n"
            + "referer:https://www.jisilu.cn/data/qdii/\n"
            + "sec-ch-ua:\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\n"
            + "sec-ch-ua-mobile:?0\n"
            + "sec-ch-ua-platform:\"Windows\"\n"
            + "sec-fetch-dest:empty\n"
            + "sec-fetch-mode:cors\n"
            + "sec-fetch-site:same-origin\n"
            + "user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\n"
            + "x-requested-with:XMLHttpRequest";
        return Arrays.stream(headersAsString.split("\n"))
            .filter(e -> !e.startsWith("Content-Length:"))
            .collect(
                Collectors.toMap(
                    e -> StringUtils.substringBefore(e, ":").strip(),
                    e -> StringUtils.substringAfter(e, ":").strip()
                )
            );
    }

    /**
     * @see <a href="https://www.jisilu.cn/data/qdii/detail/159509">页面地址</a>
     */
    public static EtfHistoryDetailsResp getHistoryDetails(String code) {
        String url = "https://www.jisilu.cn/data/qdii/detail_hists/?___jsl=LST___t={ts}";
        String replacedUrl = StringUtils.replace(url, "{ts}", String.valueOf(System.currentTimeMillis()));
        Map<String, String> headers = getHeaderMap();
        Map<String, String> formData = new HashMap<>();
        formData.put("is_search", "1");
        formData.put("fund_id", code);
        formData.put("rp", "50");
        formData.put("page", "1");
        String s = HttpClient4Utils.doPost(replacedUrl, formData, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, EtfHistoryDetailsResp.class);
    }
}
