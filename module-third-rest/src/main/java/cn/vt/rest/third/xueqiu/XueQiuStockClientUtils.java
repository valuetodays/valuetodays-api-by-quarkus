package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.xueqiu.vo.XueQiuKlineResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuMinuteChartResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuQuoteDetailResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuStockRealtimeQuoteData;
import cn.vt.rest.third.xueqiu.vo.XueQiuStockRealtimeQuoteResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-23
 */
public class XueQiuStockClientUtils {
    /**
     * 获取分钟数据
     * 注意！！：需要cookie.
     *
     * @param codeWithRegion like SH600036
     * @param period         1d or 5d
     * @param cookie         cookie
     */
    public static XueQiuMinuteChartResp minuteChart(String codeWithRegion,
                                                    String period,
                                                    String cookie) {
        Map<String, String> headers = buildHeaders(cookie);
        String url = "https://stock.xueqiu.com/v5/stock/chart/minute.json?symbol={codeWithRegion}&period={period}";
        String replacedUrl = StringUtils.replace(url, "{codeWithRegion}", codeWithRegion);
        replacedUrl = StringUtils.replace(replacedUrl, "{period}", period);
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, XueQiuMinuteChartResp.class);
    }

    /**
     * @param codeWithRegion like SH600036
     * @param cookieText     cookie
     */
    public static XueQiuMinuteChartResp minuteChart1d(String codeWithRegion, String cookieText) {
        return minuteChart(codeWithRegion, "1d", cookieText);
    }

    /**
     * @param codeWithRegion like SH600036
     * @param cookieText     cookie
     */
    public static XueQiuMinuteChartResp minuteChart5d(String codeWithRegion, String cookieText) {
        return minuteChart(codeWithRegion, "5d", cookieText);
    }


    //            + "&period=day&type=before"
//            + "&count=" + (-284)
//            + "&indicator=kline,pe,pb,ps,pcf,market_capital,agt,ggt,balance";

    /**
     * @param codeWithRegion like SH600036
     * @param begin          timestamp
     * @param count          -242
     * @param cookie         cookie
     */
    public static XueQiuKlineResp kline(String codeWithRegion,
                                        long begin,
                                        int count,
                                        String cookie
    ) {
        Map<String, String> headers = buildHeaders(cookie);
        String url = "https://stock.xueqiu.com"
            + "/v5/stock/chart/kline.json?symbol={codeWithRegion}&begin={begin}&type=before&count={c}"
            + "&period=day&indicator=kline,pe,pb,ps,pcf,market_capital,agt,ggt,balance";
        String replacedUrl = StringUtils.replace(url, "{codeWithRegion}", codeWithRegion);
        replacedUrl = StringUtils.replace(replacedUrl, "{begin}", String.valueOf(begin));
        replacedUrl = StringUtils.replace(replacedUrl, "{c}", String.valueOf(count));
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, XueQiuKlineResp.class);
    }

    /**
     * @param codeWithRegion like SH600036
     * @param count          count
     * @param cookie         cookie
     */
    public static XueQiuKlineResp kline(String codeWithRegion,
                                        int count,
                                        String cookie) {
        return kline(codeWithRegion, System.currentTimeMillis(), count, cookie);
    }

    /**
     * @param codeWithRegion like SH600036
     * @param cookie         cookie
     */
    public static XueQiuKlineResp kline(String codeWithRegion,
                                        String cookie) {
        return kline(codeWithRegion, -240, cookie);
    }

    /**
     * @param nullableCookie nullableCookie
     */
    private static Map<String, String> buildHeaders(String nullableCookie) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Referer", "https://xueqiu.com");
        headers.put("origin", "https://xueqiu.com");
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
                "sec-ch-ua-platform", "\"Windows\""
            )
        );
        return headers;
    }

    /**
     * pageUrl = "<a href="https://xueqiu.com/S/SH000001">...</a>";
     *
     * @param codes like "SH600036,SZ002142"
     * @param ts    System.currentTimeMillis()
     */
    public static XueQiuStockRealtimeQuoteResp realtimeQuote(String codes, long ts) {
        Map<String, String> headers = buildHeaders(null);
        String url = "https://stock.xueqiu.com"
            + "/v5/stock/realtime/quotec.json?symbol={codes}&_={ts}";
        String replacedUrl = StringUtils.replace(url, "{codes}", codes);
        replacedUrl = StringUtils.replace(replacedUrl, "{ts}", String.valueOf(ts));
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, XueQiuStockRealtimeQuoteResp.class);
    }

    /**
     * @param codes like "SH600036,SZ002142"
     */
    public static XueQiuStockRealtimeQuoteResp realtimeQuote(String codes) {
        return realtimeQuote(codes, System.currentTimeMillis());
    }

    /**
     * @param code like "SH600036"
     */
    public static XueQiuStockRealtimeQuoteData realtimeQuoteOne(String code) {
        XueQiuStockRealtimeQuoteResp resp = realtimeQuote(code);
        List<XueQiuStockRealtimeQuoteData> dataList = resp.getData();
        int errorCode = resp.getError_code();
        if (errorCode != 0) {
            throw new IllegalStateException("errorCode should be 0");
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        int size = dataList.size();
        if (size > 1) {
            throw new IllegalStateException("dataList.size() should be 1");
        }
        return dataList.get(0);
    }

    /**
     * 可以获取到iopv，每次只支持查一条数据
     *
     * @param code   SH600036
     * @param cookie cookie
     */
    public static XueQiuQuoteDetailResp quoteDetail(String code, String cookie) {
        Map<String, String> headers = buildHeaders(cookie);
        String url = "https://stock.xueqiu.com"
            + "/v5/stock/quote.json?symbol={code}&extend=detail";
        String replacedUrl = StringUtils.replace(url, "{code}", code);
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, XueQiuQuoteDetailResp.class);
    }
}
