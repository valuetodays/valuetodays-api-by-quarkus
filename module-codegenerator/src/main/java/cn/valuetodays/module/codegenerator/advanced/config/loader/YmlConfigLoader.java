package cn.valuetodays.module.codegenerator.advanced.config.loader;

import cn.valuetodays.module.codegenerator.advanced.config.ConfigLoader;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lei.liu
 * @since 2019-10-09 17:51
 */
public class YmlConfigLoader implements ConfigLoader {

    @Override
    public CgConfig load(InputStream inputStream) throws IOException {
//        return yaml.loadAs(inputStream, CgConfig.class);
//        List list = yaml.loadAs(inputStream, List.class);
//        System.out.println(list);
        //yaml格式配置文件转成配置POJO
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(inputStream, CgConfig.class);
    }

}
