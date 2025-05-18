package cn.valuetodays.api2.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("greeting")
@Data
public class GreetingProperties {
    private String title;
    private String version;
}
