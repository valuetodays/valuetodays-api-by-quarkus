package cn.valuetodays.module.codegenerator.advanced.generate;

import java.io.IOException;

/**
 * @author lei.liu
 * @since 2018-09-06 16:02:11
 */
public class StringBaseTemplate extends BaseTemplate {

    public StringBaseTemplate() {
    }

    public StringBaseTemplate(String templateSrc, String dst, String suffix, boolean filterSourceFilePath,
                              boolean filterDestinationFilePath,
                              int sort
    ) {
        super(templateSrc, dst, suffix, filterSourceFilePath, filterDestinationFilePath, sort);
    }

    @Override
    protected void generate() throws IOException {

    }

    @Override
    public void afterPropertiesSet() {

    }
}
