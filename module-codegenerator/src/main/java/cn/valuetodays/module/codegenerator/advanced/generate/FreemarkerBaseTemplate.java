package cn.valuetodays.module.codegenerator.advanced.generate;

import cn.valuetodays.module.codegenerator.pojo.TableClass;
import cn.valuetodays.module.codegenerator.util.StringExUtil;
import cn.vt.util.FreemarkerUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lei.liu
 * @since 2018-09-06 15:59:04
 */
public abstract class FreemarkerBaseTemplate extends BaseTemplate {

    public FreemarkerBaseTemplate() {
        super();
    }

    public File getTargetFile() {
        Map<String, Object> params = getMapForFilterPath();

        String targetDir = getCgConfig().getProject().getTargetBaseDir()
            + "/" + getCgConfig().getProject().getFormattedName() + getDstDir();
        if (isFilterDstPath()) {
            targetDir = FreemarkerUtils.render(targetDir, params);
        }
        if (!targetDir.endsWith("/")) {
            targetDir += "/";
        }
        String targetPath = targetDir;
        if (isFilterDstPath()) {
            targetPath += FreemarkerUtils.render(
                StringExUtil.trimToEmpty(getSuffix()), params
            );
        } else {
            targetPath += getSuffix();
        }

        File file = new File(targetPath);
        if (!file.getParentFile().mkdirs()) {
//            throw new RuntimeException("");
        }
        return file;
    }

    private Map<String, Object> getMapForFilterPath() {
        FreemarkerTemplateConf freemarkerTemplateConf = (FreemarkerTemplateConf) getConfiguration();
        Map<String, Object> context = freemarkerTemplateConf.getContext();
        TableClass pojo = (TableClass) context.get("pojo");

        final Map<String, Object> map = new HashMap<>();
        map.put("pojo", pojo);
        Properties resourceFilter = getCgConfig().getResourceFilter();
        resourceFilter.stringPropertyNames().forEach(e -> {
            map.put(e, resourceFilter.get(e));
        });
        return map;
    }

    @Override
    public void afterPropertiesSet() {
        FreemarkerTemplateConf conf = FreemarkerTemplateConf.newInstance(getCgConfig());
        super.setConfiguration(conf);
    }
}
