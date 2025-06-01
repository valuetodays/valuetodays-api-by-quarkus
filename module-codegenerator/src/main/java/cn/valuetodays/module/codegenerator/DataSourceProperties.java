package cn.valuetodays.module.codegenerator;

import io.smallrye.config.ConfigMapping;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@ConfigMapping(prefix = "quarkus.datasource")
public interface DataSourceProperties {
    String username();

    String password();
}
