package cn.valuetodays.api2.web;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-10-22
 */
public interface ICookieCacheComponent {
    String doGetAndBuildToString(String domain);

    String doGetAndBuildToString(String domain, List<String> excludeNames);

    String pullCookie(String domain);

    Map<String, String> pullXueqiuCookie();

    Map<String, String> pull(String domain, List<String> excludeNames);
}
