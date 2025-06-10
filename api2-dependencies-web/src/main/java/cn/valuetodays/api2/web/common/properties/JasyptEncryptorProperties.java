package cn.valuetodays.api2.web.common.properties;

import io.smallrye.config.ConfigMapping;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@ConfigMapping(prefix = "jasypt.encryptor")
public interface JasyptEncryptorProperties {
    String password();
}
