package cn.valuetodays.module.codegenerator.advanced.config.loader;

import cn.valuetodays.module.codegenerator.advanced.config.ConfigLoader;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liulei-home
 * @since 2018-09-06 22:32
 */
public class XmlConfigLoader implements ConfigLoader {

    @Override
    public CgConfig load(InputStream inputStream) throws IOException {
        throw new UnsupportedOperationException("not support since V109");
    }

}
