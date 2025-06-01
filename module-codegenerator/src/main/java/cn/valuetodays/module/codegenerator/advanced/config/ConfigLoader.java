package cn.valuetodays.module.codegenerator.advanced.config;

import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lei.liu
 * @since 2019-10-09 17:43
 */
public interface ConfigLoader {
    CgConfig load(InputStream inputStream) throws IOException;
}
