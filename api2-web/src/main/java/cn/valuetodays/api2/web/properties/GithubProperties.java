package cn.valuetodays.api2.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-25
 */
@Data
@ConfigurationProperties(prefix = "vt.github")
public class GithubProperties {
    private String gitToken;
    private String username;
    private String email;
}
