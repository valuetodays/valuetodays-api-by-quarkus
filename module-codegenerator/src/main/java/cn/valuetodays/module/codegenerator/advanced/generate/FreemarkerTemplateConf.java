package cn.valuetodays.module.codegenerator.advanced.generate;

import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import cn.vt.util.FreemarkerUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lei.liu
 * @since 2018-09-06 16:57:35
 */
@Slf4j
@Getter
@Setter
public class FreemarkerTemplateConf extends TemplateConf {
    private Configuration configuration;

    public static FreemarkerTemplateConf newInstance(CgConfig cgConfig) {
        Map<String, Object> root = new HashMap<>();
        FreemarkerTemplateConf conf = new FreemarkerTemplateConf();
        conf.setContext(root);
        Properties resourceFilter = cgConfig.getResourceFilter();

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);
        if (cgConfig.getFiles().isUseAbsolutePath()) {
            String tplFileDir = cgConfig.getFiles().getTplFileDir();
            String targetPath = FreemarkerUtils.renderWithProperties(tplFileDir, resourceFilter);
            if (StringUtils.isEmpty(targetPath)) {
                throw new RuntimeException("missing [tplFileDir] in [files] property");
            }
            try {
                cfg.setDirectoryForTemplateLoading(new File(targetPath));
            } catch (IOException e) {
                log.error("cfg.setDirectoryForTemplateLoading", e);
            }
        } else {
            String tplResourceDir = cgConfig.getFiles().getTplResourceDir();
            String targetResourceDir = FreemarkerUtils.renderWithProperties(tplResourceDir, resourceFilter);
            if (StringUtils.isEmpty(targetResourceDir)) {
                throw new RuntimeException("missing [tplResourceDir] in [files] property");
            }
            cfg.setClassForTemplateLoading(FreemarkerTemplateConf.class, targetResourceDir);
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        conf.setConfiguration(cfg);
        return conf;
    }
}
