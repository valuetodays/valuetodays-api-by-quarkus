package cn.vt.rest.third.danjuan;

import cn.vt.rest.third.danjuan.vo.DjIndexResp;
import cn.vt.rest.third.danjuan.vo.DjPbResp;
import cn.vt.rest.third.danjuan.vo.DjPeResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-24
 */
public class DanjuanQuoteClientUtils {
    /**
     * 指数估值
     */
    public static DjIndexResp evaluationList() {
        String url = "https://danjuanapp.com/djapi/index_eva/dj";
        String s = HttpClient4Utils.doGet(url, null, null, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, DjIndexResp.class);
    }

    /**
     * pb历史.
     *
     * @param region "SH" or "SZ"
     * @param code   513300
     * @param day    all=近10年 3y=近3年
     */
    public static DjPbResp pbHistory(String region, String code, String day) {
        String url = "https://danjuanapp.com/djapi/index_eva/pb_history/{region}{code}?day={day}";
        String replacedUrl = StringUtils.replace(url, "{region}", String.valueOf(region));
        replacedUrl = StringUtils.replace(replacedUrl, "{code}", String.valueOf(code));
        replacedUrl = StringUtils.replace(replacedUrl, "{day}", String.valueOf(day));
        String s = HttpClient4Utils.doGet(replacedUrl, null, null, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, DjPbResp.class);
    }

    /**
     * pe历史.
     *
     * @param region "SH" or "SZ"
     * @param code   513300
     * @param day    all=近10年 3y=近3年
     */
    public static DjPeResp peHistory(String region, String code, String day) {
        String url = "https://danjuanapp.com/djapi/index_eva/pe_history/{region}{code}?day={day}";
        String replacedUrl = StringUtils.replace(url, "{region}", String.valueOf(region));
        replacedUrl = StringUtils.replace(replacedUrl, "{code}", String.valueOf(code));
        replacedUrl = StringUtils.replace(replacedUrl, "{day}", String.valueOf(day));
        String s = HttpClient4Utils.doGet(replacedUrl, null, null, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, DjPeResp.class);
    }

}
