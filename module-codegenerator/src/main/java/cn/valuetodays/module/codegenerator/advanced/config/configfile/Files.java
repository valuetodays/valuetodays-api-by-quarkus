package cn.valuetodays.module.codegenerator.advanced.config.configfile;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.generate.BaseTemplate;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Files implements AfterPropertiesSet {
    private List<BaseTemplate> fileList = new ArrayList<>();
    private String dstDir;
    private boolean filterDstPath = true;
    private boolean useAbsolutePath = false;
    private String tplFileDir;
    private String tplResourceDir;

    private CgConfig cgConfig;

    @Override
    public void afterPropertiesSet() {
        fileList.forEach(e -> {
            e.setUseAbsolutePath(useAbsolutePath);
            e.setCgConfig(cgConfig);
            if (e.isFilterDstPath()) {
                e.setFilterDstPath(filterDstPath);
            }
            e.afterPropertiesSet();
        });
    }
}
