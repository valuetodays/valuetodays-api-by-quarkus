package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.IopvResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-21
 */
public class EtfClientUtils {

    /**
     * @param code       513360
     * @param page       [1, )
     * @param jsCallback string value
     * @return IopvResp
     * @see <a href="https://fundf10.eastmoney.com/jjjz_513300.html">source page</a>
     */
    public static IopvResp getIopv(String code, int page, String jsCallback) {
        String url = "https://api.fund.eastmoney.com/f10/lsjz?"
            + "pageSize=20&startDate=&endDate=";
        url += "&fundCode=" + code;
        url += "&pageIndex=" + page;
        url += "&callback=" + jsCallback;
        url += "&_=" + System.currentTimeMillis();
        String headersAsString = ""
            + "Accept: */*\n"
            + "Accept-Encoding: gzip, deflate, br, zstd\n"
            + "Accept-Language: en,zh-CN;q=0.9,zh;q=0.8\n"
            + "Connection: keep-alive\n"
            + "Cookie: qgqp_b_id=59abc0ffcdfaebd6af74db9821a98f16; EMFUND1=null; EMFUND2=null; EMFUND3=null; EMFUND4=null; EMFUND5=null; EMFUND6=null; EMFUND7=null; EMFUND8=null; EMFUND0=null; HAList=ty-1-513300-%u7EB3%u65AF%u8FBE%u514BETF; EMFUND9=12-21 16:12:16@#$%u534E%u590F%u7EB3%u65AF%u8FBE%u514B100ETF%28QDII%29@%23%24513300\n"
            + "DNT: 1\n"
            + "Host: api.fund.eastmoney.com\n"
            + "Referer: https://fundf10.eastmoney.com/\n"
            + "Sec-Fetch-Dest: script\n"
            + "Sec-Fetch-Mode: no-cors\n"
            + "Sec-Fetch-Site: same-site\n"
            + "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\n"
            + "sec-ch-ua: \"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\n"
            + "sec-ch-ua-mobile: ?0\n"
            + "sec-ch-ua-platform: \"Windows\"";
        Map<String, String> headers = Arrays.stream(headersAsString.split("\n"))
            .filter(e -> !e.startsWith("Content-Length:"))
            .collect(
                Collectors.toMap(
                    e -> StringUtils.substringBefore(e, ":").strip(),
                    e -> StringUtils.substringAfter(e, ":").strip()
                )
            );
        String s = HttpClient4Utils.doGet(url, null, headers, null);
        String jsonStr = EastMoneyCommons.substringBusiJsonString(s);
        return JsonUtils.fromJson(jsonStr, IopvResp.class);
    }

    /**
     * @param code 513360
     * @param page [1, )
     */
    public static IopvResp getIopv(String code, int page) {
        String jsCb = "jQuery183044371271243813104_1734768752179";
        return getIopv(code, page, jsCb);
    }
}
