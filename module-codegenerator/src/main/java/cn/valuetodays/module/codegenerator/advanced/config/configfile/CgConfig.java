package cn.valuetodays.module.codegenerator.advanced.config.configfile;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.config.Datasource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class CgConfig implements AfterPropertiesSet {
    private Project project;
    private TableNameFilter tableNameFilter;
    private Datasource activeDatasource;
    private MetaProperties properties;
    @Setter(AccessLevel.NONE)
    private Properties resourceFilter = new Properties();
    private Files files;

    @Override
    public void afterPropertiesSet() {
        // 添加系统环境变量和系统配置
        resourceFilter.putAll(System.getenv());
        Properties systemProperties = System.getProperties();
        for (String stringPropertyName : systemProperties.stringPropertyNames()) {
            resourceFilter.put(stringPropertyName, systemProperties.getProperty(stringPropertyName));
        }

        if (this.properties != null) {
            this.properties.afterPropertiesSet();
            resourceFilter.putAll(this.properties.toMap());
        }

        tableNameFilter.afterPropertiesSet();

        files.setCgConfig(this);
        files.afterPropertiesSet();
    }

}
