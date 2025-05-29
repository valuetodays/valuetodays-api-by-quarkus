package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.utils.NumberUtils;
import cn.vt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-21
 */
public final class EastMoneyCommons {
    public static final BigDecimal bg1000 = BigDecimal.valueOf(1000);
    public static final BigDecimal bg100 = BigDecimal.valueOf(100);

    private EastMoneyCommons() {
    }

    /**
     * get substring from respString.
     */
    public static String substringBusiJsonString(String respString) {
        int beginInx = respString.indexOf("({");
        int endInx = respString.lastIndexOf("})");
        String substring = respString.substring(beginInx + "({".length(), endInx);
        return "{" + substring + "}";
    }

    public static <T> T parseResponseAsObj(String responseString, Class<T> respClass) {
        String jsonString = substringBusiJsonString(responseString);
        return JsonUtils.fromJson(jsonString, respClass);
    }

    public static BigDecimal parseAsBigDecimalDivide100(String str) {
        return BigDecimal.valueOf(NumberUtils.divide3(parseAsBigDecimal(str), bg100));
    }

    public static BigDecimal parseAsBigDecimalDivide1000(String str) {
        return BigDecimal.valueOf(NumberUtils.divide3(parseAsBigDecimal(str), bg1000));
    }

    public static BigDecimal parseAsBigDecimal(String str) {
        if (StringUtils.equalsAny(str, null, "", "-")) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(str);
    }
}
