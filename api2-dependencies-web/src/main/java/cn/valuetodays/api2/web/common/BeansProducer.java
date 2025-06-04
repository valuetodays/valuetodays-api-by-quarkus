package cn.valuetodays.api2.web.common;

import cn.valuetodays.quarkus.commons.base.auth.TokenInCacheAuthUserParser;
import cn.vt.auth.AuthUserParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/**
 * this class to expose bean that can not be injected automatically.
 *
 * @author lei.liu
 * @since 2025-06-04
 */
@ApplicationScoped
public class BeansProducer {
    @Produces
    public AuthUserParser produce() {
        return new TokenInCacheAuthUserParser();
    }
}
