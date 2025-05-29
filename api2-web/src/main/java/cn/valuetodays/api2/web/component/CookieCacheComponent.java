package cn.valuetodays.api2.web.component;

import cn.vt.util.JsonUtils;
import cn.vt.util.RSAUtils;
import cn.vt.vo.NameValueVo;
import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 从cache中获取cookie，并转化成<span>key1=value1; key2=value2; key3...</span>这样的值.
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@Component
@Slf4j
public class CookieCacheComponent {
    public static final String KEY = "_quote_api_cookie";
    public static final String cacheKey = "cache_rsa_pair";
    public static final String fieldPubKey = "pub";
    public static final String fieldPriKey = "pri";

    @Autowired
    private RedisDataSource stringRedisTemplate;

    public String doGetAndBuildToString(String domain) {
        return doGetAndBuildToString(domain, List.of());
    }

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
