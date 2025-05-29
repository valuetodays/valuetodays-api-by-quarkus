package cn.vt.rest.third.sse;

import cn.vt.rest.third.sse.vo.StockNewAccountResp;
import cn.vt.util.HttpClient4Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@Slf4j
public class SseNewStockAccountClientUtils {
    private SseNewStockAccountClientUtils() {

    }

    /**
     * 股民新开户数
     *
     * @param yyyyMM yyyyMM
     * @see <a href="https://www.sse.com.cn/aboutus/publication/monthly/investor/">page</a>
     */
    public static StockNewAccountResp getStockNewAccount(String yyyyMM) {
        String url = "https://query.sse.com.cn/commonQuery.do";
        Map<String, String> headerMap = SseEtfClientUtils.parseAsHeaderMap();

        Map<String, String> queryString = new HashMap<>();
        SseCommonCodes.fillJsonCallback(queryString, "jsonpCallback65433875");
        SseCommonCodes.fillSqlId(queryString, "COMMON_SSE_TZZ_M_ALL_ACCT_C");
        SseCommonCodes.fillUnderscore(queryString);
        SseCommonCodes.fillNoPage(queryString);

        queryString.put("MDATE", yyyyMM);

        String respString = HttpClient4Utils.doGet(url, queryString, headerMap, null);
        return SseCommonCodes.parseResponseAsObj(respString, queryString, StockNewAccountResp.class);
    }

}
