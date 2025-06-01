package cn.valuetodays.module.codegenerator.advanced.config.configfile;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2021-09-14 17:03
 */
@Getter
@Setter
public class MetaProperties implements AfterPropertiesSet {
    private String basePackage;
    @Setter(AccessLevel.NONE)
    private String basePackageAsPath;
    private String author;
    private String moduleName;
    private boolean useAtSince = true;

    @Override
    public void afterPropertiesSet() {
        basePackageAsPath = basePackage.replace(".", "/");
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("basePackage", basePackage);
        map.put("basePackageAsPath", basePackageAsPath);
        map.put("author", author);
        map.put("moduleName", moduleName);
        map.put("useAtSince", String.valueOf(useAtSince));
        return map;
    }
}
