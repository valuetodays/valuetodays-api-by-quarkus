package cn.valuetodays.module.codegenerator.advanced.generate;

import cn.valuetodays.module.codegenerator.pojo.TableClass;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author lei.liu
 * @since 2018-09-06 15:59:04
 */
public class FreemarkerFileBaseTemplate extends FreemarkerBaseTemplate {

    public FreemarkerFileBaseTemplate() {
        super();
    }

    @Override
    protected void generate() throws IOException {
        FreemarkerTemplateConf freemarkerTemplateConf = (FreemarkerTemplateConf) getConfiguration();
        Map<String, Object> context = freemarkerTemplateConf.getContext();
        TableClass pojo = (TableClass) context.get("pojo");

        final File file = getTargetFile();

        generateFile0(file, freemarkerTemplateConf, pojo, getTplSrc());
    }

    private void generateFile0(File file,
                               FreemarkerTemplateConf freemarkerTemplateConf,
                               TableClass pojo,
                               String templatePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            Configuration configuration = freemarkerTemplateConf.getConfiguration();
            freemarker.template.Template template = configuration.getTemplate(templatePath);
            template.process(freemarkerTemplateConf.getContext(), bufferedWriter);
            bufferedWriter.flush();
        } catch (TemplateException e) {
            throw new IOException(" error when dealing with table " + pojo.getTableName(), e);
        }
        getLogger().debug("file [{}] was created.", file.getAbsolutePath());
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }
}
