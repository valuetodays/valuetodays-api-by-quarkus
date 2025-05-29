package cn.vt.rest.third.sse;

import cn.vt.rest.third.sse.vo.EtfDailyVolumeResp;
import cn.vt.rest.third.sse.vo.FundListResp;
import cn.vt.rest.third.sse.vo.PageHelp;
import cn.vt.rest.third.sse.vo.TotalSharesResp;
import cn.vt.rest.third.sse.vo.TradeInfoResp;
import cn.vt.util.DateUtils;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-01
 */
@Slf4j
public class SseEtfClientUtils {
    private SseEtfClientUtils() {
    }

    /**
     * 黄金etf
     *
     * @see <a href="https://www.sse.com.cn/assortment/fund/etf/list/">页面地址</a>
     */
    public static List<FundListResp.FundListItem> getGoldenEtfList() {
        return getEtfList("06");
    }

    /**
     * 跨境etf
     *
     * @see <a href="https://www.sse.com.cn/assortment/fund/etf/list/">页面地址</a>
     */
    public static List<FundListResp.FundListItem> getQdiiEtfList() {
        return getEtfList("33");
    }

    /**
     * @see <a href="https://www.sse.com.cn/assortment/fund/etf/list/">页面地址</a>
     */
    public static List<FundListResp.FundListItem> getEtfList(String subClass) {
        Map<String, String> headerMap = parseAsHeaderMap();

        int currentPage = 1;
        List<FundListResp.FundListItem> all = new ArrayList<>(100);
        while (true) {
            FundListResp fundListResp = pageQueryEtfList(headerMap, currentPage, subClass);
            if (Objects.nonNull(fundListResp)) {
                // 是否是最后一页
                PageHelp<FundListResp.FundListItem> pageHelp = fundListResp.getPageHelp();
                int pageCount = pageHelp.getPageCount();
                if (currentPage > pageCount) {
                    return all;
                }
                List<FundListResp.FundListItem> result = fundListResp.getResult();
                all.addAll(result);
                // 已是最后一页，就不需要再查询一次了
                if (all.size() >= pageHelp.getTotal()) {
                    return all;
                }
            }
            currentPage++;
        }
    }

    private static Map<String, String> parseAsHeaderMap(String headerAsString) {
        return Arrays.stream(headerAsString.split("\n"))
            .filter(e -> !e.startsWith("Content-Length"))
            .collect(
                Collectors.toMap(
                    e -> StringUtils.substringBefore(e, ":").strip(),
                    e -> StringUtils.substringAfter(e, ":").strip()
                )
            );
    }


    static Map<String, String> parseAsHeaderMap() {
        String headersAsString = """
            accept:             */*
            accept-encoding:            gzip, deflate, br, zstd
            accept-language:            en,zh-CN;q=0.9,zh;q=0.8
            connection:            keep-alive
            cookie:            gdp_user_id=gioenc-g6g59c97%2C5g64%2C5bc5%2Cc4e0%2C9d858719013b; VISITED_MENU=%5B%229592%22%5D; ba17301551dcbaf9_gdp_session_id=599e65ef-2aba-4272-91ca-ee38a8000fa7; ba17301551dcbaf9_gdp_session_id_sent=599e65ef-2aba-4272-91ca-ee38a8000fa7; ba17301551dcbaf9_gdp_sequence_ids={%22globalKey%22:542%2C%22VISIT%22:5%2C%22PAGE%22:28%2C%22VIEW_CLICK%22:511}
            dnt:            1
            host:            query.sse.com.cn
            referer:             https://www.sse.com.cn/
            sec-ch-ua:            "Google Chrome";v="131", "Chromium";v="131", "Not_A Brand";v="24"
            sec-ch-ua-mobile:            ?0
            sec-ch-ua-platform:            "Windows"
            sec-fetch-dest:            script
            sec-fetch-mode:            no-cors
            sec-fetch-site:            same-site
            user-agent:            Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36
            """;
        return parseAsHeaderMap(headersAsString);
    }


    private static FundListResp pageQueryEtfList(Map<String, String> headerMap, int currentPage, String subClass) {
        String url = "https://query.sse.com.cn/commonSoaQuery.do";


        Map<String, String> queryString = new HashMap<>();
        SseCommonCodes.fillJsonCallback(queryString, "jsonpCallback15446944");
        SseCommonCodes.fillSqlId(queryString, "FUND_LIST");
        SseCommonCodes.fillPageHelp(queryString, currentPage, 25);
        queryString.put("pagecache", "false");
        queryString.put("fundType", "00");
        queryString.put("subClass", subClass);
        SseCommonCodes.fillUnderscore(queryString);

        String respString = HttpClient4Utils.doGet(url, queryString, headerMap, null);
        return SseCommonCodes.parseResponseAsObj(respString, queryString, FundListResp.class);
    }


    /**
     * 获取总份额（指定code和date）
     *
     * @param code 513100
     * @param date 2025-01-03
     * @see <a href="https://www.sse.com.cn/assortment/fund/list/etfinfo/basic/index.shtml?FUNDID=513110">页面地址</a>
     */
    public static TotalSharesResp getTotalVolumeInWan(String code, LocalDate date) {
        String url = "https://query.sse.com.cn/commonQuery.do";
        Map<String, String> headerMap = parseAsHeaderMap();

        Map<String, String> queryString = new HashMap<>();
        SseCommonCodes.fillJsonCallback(queryString, "jsonpCallback36643666");
        SseCommonCodes.fillSqlId(queryString, "COMMON_SSE_ZQPZ_ETFZL_ETFJBXX_JJGM_SEARCH_L");
        SseCommonCodes.fillNoPage(queryString);
        queryString.put("SEC_CODE", code);
        queryString.put("STAT_DATE", DateUtils.formatDate(date.atStartOfDay()));
        SseCommonCodes.fillUnderscore(queryString);

        String respString = HttpClient4Utils.doGet(url, queryString, headerMap, null);
        return SseCommonCodes.parseResponseAsObj(respString, queryString, TotalSharesResp.class);
    }

    /**
     * 获取总份额
     *
     * @param date date
     * @see <a href="https://www.sse.com.cn/market/funddata/volumn/etfvolumn/">页面地址</a>
     */
    public static List<EtfDailyVolumeResp.Item> getDailyVolumes(LocalDate date) {
        Map<String, String> headerMap = parseAsHeaderMap();

        int currentPage = 1;
        List<EtfDailyVolumeResp.Item> all = new ArrayList<>(100);
        while (true) {
            EtfDailyVolumeResp dailyVolumeResp = pageQueryDailyVolumes(headerMap, currentPage, date);
            if (Objects.nonNull(dailyVolumeResp)) {
                // 是否是最后一页
                PageHelp<EtfDailyVolumeResp.Item> pageHelp = dailyVolumeResp.getPageHelp();
                int pageCount = pageHelp.getPageCount();
                if (currentPage > pageCount) {
                    return all;
                }
                List<EtfDailyVolumeResp.Item> result = dailyVolumeResp.getResult();
                all.addAll(result);
                // 已是最后一页，就不需要再查询一次了
                if (all.size() >= pageHelp.getTotal()) {
                    return all;
                }
            }
            currentPage++;
            SleepUtils.sleep(200);
        }
    }

    /**
     * 获取总份额(指定分页数)
     *
     * @param date        2025-01-03
     * @param headerMap   headerMap
     * @param currentPage currentPage
     * @see <a href="https://www.sse.com.cn/market/funddata/volumn/etfvolumn/">页面地址</a>
     */
    private static EtfDailyVolumeResp pageQueryDailyVolumes(Map<String, String> headerMap, int currentPage, LocalDate date) {
        String url = "https://query.sse.com.cn/commonQuery.do";

        Map<String, String> queryString = new HashMap<>();
        SseCommonCodes.fillJsonCallback(queryString, "jsonpCallback37565880");
        SseCommonCodes.fillSqlId(queryString, "COMMON_SSE_ZQPZ_ETFZL_XXPL_ETFGM_SEARCH_L");
        SseCommonCodes.fillPageHelp(queryString, currentPage, 25);
        queryString.put("STAT_DATE", DateUtils.formatDate(date.atStartOfDay()));
        SseCommonCodes.fillUnderscore(queryString);

        String respString = HttpClient4Utils.doGet(url, queryString, headerMap, null);
        return SseCommonCodes.parseResponseAsObj(respString, queryString, EtfDailyVolumeResp.class);
    }

    /**
     * 获取交易信息
     *
     * @param code 513100
     * @param date 2025-01-03
     * @see <a href="https://www.sse.com.cn/assortment/fund/list/etfinfo/basic/index.shtml?FUNDID=513110">页面地址</a>
     */
    public static TradeInfoResp getTradeInfo(String code, LocalDate date) {
        String url = "https://query.sse.com.cn/commonQuery.do";
        Map<String, String> headerMap = parseAsHeaderMap();

        Map<String, String> queryString = new HashMap<>();
        SseCommonCodes.fillJsonCallback(queryString, "jsonpCallback97399504");
        SseCommonCodes.fillSqlId(queryString, "COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C");
        SseCommonCodes.fillUnderscore(queryString);
        queryString.put("SEC_CODE", code);
        queryString.put("TX_DATE", DateUtils.formatDate(date.atStartOfDay()));

        String respString = HttpClient4Utils.doGet(url, queryString, headerMap, null);
        return SseCommonCodes.parseResponseAsObj(respString, queryString, TradeInfoResp.class);
    }
}
