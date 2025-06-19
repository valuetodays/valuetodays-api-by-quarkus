package cn.valuetodays.api2.web.component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.valuetodays.api2.web.ICookieCacheComponent;
import cn.vt.rest.third.xueqiu.vo.PushCookieReq;
import cn.vt.util.JsonUtils;
import cn.vt.util.RSAUtils;
import cn.vt.vo.NameValueVo;
import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 从cache中获取cookie，并转化成<span>key1=value1; key2=value2; key3...</span>这样的值.
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@ApplicationScoped
@Slf4j
public class CookieCacheComponent implements ICookieCacheComponent {
    public static final String KEY = "_quote_api_cookie";
    public static final String cacheKey = "cache_rsa_pair";
    public static final String fieldPubKey = "pub";
    public static final String fieldPriKey = "pri";

    @Inject
    RedisDataSource stringRedisTemplate;

    @Override
    public String doGetAndBuildToString(String domain) {
        return doGetAndBuildToString(domain, List.of());
    }

    @Override
    public String doGetAndBuildToString(String domain, List<String> excludeNames) {
        String cookieArrText = stringRedisTemplate.value(String.class).get(KEY + ":" + domain);
        if (StringUtils.isBlank(cookieArrText)) {
            log.warn("cookieArrText in redis is null. domain={}", domain);
            return null;
        }
        // 目前只要name/value两个值
        List<NameValueVo> cookieList = JsonUtils.fromJson(cookieArrText, new TypeReference<>() {
        });
        return cookieList.stream()
            .filter(e -> !excludeNames.contains(e.getName()))
            .map(e -> e.getName() + "=" + e.getValue())
            .collect(Collectors.joining("; "));
    }

    @Override
    public String pullCookie(String domain) {
        Map<String, String> pull = pull(domain, List.of());
        if (MapUtils.isNotEmpty(pull)) {
            return pull.get("cookies");
        }
        return null;
    }

    @Override
    public Map<String, String> pullXueqiuCookie() {
        List<String> excludeNames = List.of("XSRF-TOKEN");
        return pull(PushCookieReq.DOMAIN_XUEQIU, excludeNames);
    }

    @Override
    public Map<String, String> pull(String domain, List<String> excludeNames) {
        String c = this.doGetAndBuildToString(domain, excludeNames);
        return Map.of("cookies", StringUtils.trimToEmpty(c));
    }

    public void doPut(String domain, final String cookieArrText) {
        if (StringUtils.isNotBlank(cookieArrText)) {
            SetArgs args = new SetArgs().ex(Duration.ofDays(7));
            stringRedisTemplate.value(String.class).set(KEY + ":" + domain, cookieArrText, args);
        }
    }

    public String mergePartitions(List<String> partitions) {
        Map<String, String> hmgetted = stringRedisTemplate.hash(String.class).hmget(cacheKey, fieldPriKey);
        String priKey = hmgetted.values().iterator().next();
        if (Objects.nonNull(priKey)) {
            return partitions.stream().map(e -> RSAUtils.decrypt(e, priKey)).collect(Collectors.joining());
        }
        return null;
    }
}
