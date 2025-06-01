package cn.valuetodays.module.codegenerator.advanced.generate;

import cn.valuetodays.module.codegenerator.pojo.TableClass;
import cn.vt.util.FreemarkerUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author lei.liu
 * @since 2022-05-26 14:02:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FreemarkerStringBaseTemplate extends FreemarkerBaseTemplate {
    private String templateString;

    public FreemarkerStringBaseTemplate() {
    }

    @Override
    protected void generate() throws IOException {
        FreemarkerTemplateConf freemarkerTemplateConf = (FreemarkerTemplateConf) getConfiguration();
        final Map<String, Object> context = freemarkerTemplateConf.getContext();
        TableClass pojo = (TableClass) context.get("pojo");
        String processedString = FreemarkerUtils.render(templateString, context);
//        log.info("processedString={}", processedString);

        final File file = getTargetFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.write(processedString, fos, freemarkerTemplateConf.getConfiguration().getDefaultEncoding());
        } catch (Exception e) {
            throw new IOException(" error when dealing with table " + pojo.getTableName(), e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }
}
