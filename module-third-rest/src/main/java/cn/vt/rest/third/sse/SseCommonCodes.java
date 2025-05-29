package cn.vt.rest.third.sse;

import cn.vt.exception.CommonException;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-24
 */
public final class SseCommonCodes {
    private SseCommonCodes() {
    }

    static void fillJsonCallback(Map<String, String> queryString, String jsonCallback) {
        queryString.put("jsonCallBack", jsonCallback);
    }

    static void fillSqlId(Map<String, String> queryString, String sqlId) {
        queryString.put("sqlId", sqlId);
    }

    static void fillUnderscore(Map<String, String> queryString) {
        queryString.put("_", String.valueOf(System.currentTimeMillis()));
    }

    static void fillPageHelp(Map<String, String> queryString, int currentPage, int pageSize) {
        String currentPageAsString = Integer.toString(currentPage);
        String pageSizeAsString = Integer.toString(pageSize);

        queryString.put("isPagination", "true");
        queryString.put("pageHelp.pageSize", pageSizeAsString);
        queryString.put("pageHelp.pageNo", currentPageAsString);
        queryString.put("pageHelp.beginPage", currentPageAsString);
        queryString.put("pageHelp.cacheSize", "1");
        queryString.put("pageHelp.endPage", currentPageAsString);
    }

    static void fillNoPage(Map<String, String> queryString) {
        queryString.put("isPagination", "false");
    }

    static <T> T parseResponseAsObj(String respString, Map<String, String> queryString, Class<T> respClass) {
        String jsonCallBackValue = queryString.get("jsonCallBack");
        if (!StringUtils.contains(respString, jsonCallBackValue)) {
            throw new CommonException("error response: " + respString);
        }
        // 找到左花括号
        String jsonBeginWithFirstLeftCurly = respString.substring(
            respString.indexOf(jsonCallBackValue) + jsonCallBackValue.length() + "(".length()
        );
        String jsonString = jsonBeginWithFirstLeftCurly.substring(0, jsonBeginWithFirstLeftCurly.lastIndexOf(")"));
        return JsonUtils.fromJson(jsonString, respClass);
    }
}
