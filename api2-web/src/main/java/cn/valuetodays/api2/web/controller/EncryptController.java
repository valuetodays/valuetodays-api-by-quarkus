package cn.valuetodays.api2.web.controller;

import cn.valuetodays.quote.component.CookieCacheComponent;
import cn.vt.util.RSAUtils;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import jakarta.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/encrypt")
public class EncryptController {
    @Inject
    private RedisDataSource connection;

    @PostMapping("/anon/getPublicKey.do")
    public Map<String, Object> getPublicKey() {
        final HashCommands<String, String, String> hashCommands = connection.hash(String.class);
        Map<String, String> hmgetted = hashCommands.hmget(CookieCacheComponent.cacheKey,
            CookieCacheComponent.fieldPubKey, CookieCacheComponent.fieldPriKey);
        if (MapUtils.isNotEmpty(hmgetted)) {
            String pri = hmgetted.get(CookieCacheComponent.fieldPriKey);
            String pub = hmgetted.get(CookieCacheComponent.fieldPubKey);
            if (StringUtils.isNoneBlank(pri, pub)) {
                return Map.of("data", pub);
            }
        }
        String[] pair = RSAUtils.genKeyPair();
        String pub = pair[0];
        String pri = pair[1];
        Map<String, String> hashToPut = Map.of(CookieCacheComponent.fieldPubKey, pub, CookieCacheComponent.fieldPriKey, pri);
        hashCommands.hset(CookieCacheComponent.cacheKey, hashToPut);
        return Map.of("data", pub);
    }
}
