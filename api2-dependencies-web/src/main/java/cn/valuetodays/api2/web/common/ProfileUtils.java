package cn.valuetodays.api2.web.common;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-11
 */
@ApplicationScoped
public class ProfileUtils {

    @ConfigProperty(name = "quarkus.profile")
    String profile;

    public boolean isDev() {
        return "dev".equalsIgnoreCase(profile);
    }
}
