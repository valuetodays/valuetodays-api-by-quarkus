package cn.valuetodays.module.codegenerator.advanced.generate;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.config.TemplateType;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Data
public abstract class BaseTemplate implements AfterPropertiesSet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TemplateType type = TemplateType.FREEMARKER;
    private String tplSrc; // 源文件路径
    private String dstDir; // 目标文件路径
    private String suffix; // 文件名后续追加文本
    private boolean filterSourceFilePath = true; // 是否过滤源文件全路径中的变量
    private boolean filterDstPath = true; // 是否过滤目标文件全路径中的变量
    private boolean useAbsolutePath;
    private int sort;
    private CgConfig cgConfig;
    private TemplateConf configuration;

    public BaseTemplate() {

    }

    public BaseTemplate(String tplSrc,
                        String dst, String suffix,
                        boolean filterSourceFilePath,
                        boolean filterDestinationFilePath,
                        int sort) {
        this.tplSrc = tplSrc;
        this.dstDir = dst;
        this.suffix = suffix;
        this.filterSourceFilePath = filterSourceFilePath;
        this.filterDstPath = filterDestinationFilePath;
        this.sort = sort;
    }

    public void doGenerate() throws IOException {
        beforeGenerate();
        generate();
        afterGenerate();
    }

    void afterGenerate() {

    }

    void beforeGenerate() {

    }

    protected abstract void generate() throws IOException;

}
