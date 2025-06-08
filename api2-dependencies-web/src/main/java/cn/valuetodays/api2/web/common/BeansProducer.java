package cn.valuetodays.api2.web.common;

import cn.valuetodays.quarkus.commons.base.auth.TokenInCacheAuthUserParser;
import cn.vt.auth.AuthUserParser;
import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 * this class to expose bean that can not be injected automatically.
 *
 * @author lei.liu
 * @since 2025-06-04
 */
@ApplicationScoped
public class BeansProducer {
    @Inject
    RedisDataSource stringRedisTemplate;
    @Produces
    public AuthUserParser tokenInCacheAuthUserParser() {
        TokenInCacheAuthUserParser t = new TokenInCacheAuthUserParser();
        t.setStringRedisTemplate(stringRedisTemplate);
        return t;
    }
}
