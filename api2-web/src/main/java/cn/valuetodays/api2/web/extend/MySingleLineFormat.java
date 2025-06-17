package cn.valuetodays.api2.web.extend;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.SingleLineFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-17
 */
public class MySingleLineFormat extends SingleLineFormat {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String newUrl = StringUtils.substringBefore(url, "?") + "?...";
        return now + "|" + elapsed + "|" + category + "|connection " + connectionId + "|url " + newUrl + "|" + "" + "|" + P6Util.singleLine(sql);
    }
}
