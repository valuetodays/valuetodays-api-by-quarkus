package cn.valuetodays.module.codegenerator.entity;

import cn.valuetodays.module.codegenerator.advanced.config.Datasource;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.Files;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.MetaProperties;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.Project;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.TableNameFilter;
import cn.vt.util.ConvertUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成 - 请求.
 *
 * @author lei.liu
 * @since 2022-05-25
 */
@Data
public class GenerateReq implements Serializable {
    private Project project;
    private TableNameFilter tableNameFilter;
    private Datasource datasource;
    private MetaProperties properties;
    private Files files;

    public CgConfig toConfigFile() {
        CgConfig cgConfig = new CgConfig();
        cgConfig.setProject(ConvertUtils.convertObj(this.project, Project.class));
        cgConfig.setTableNameFilter(ConvertUtils.convertObj(this.tableNameFilter, TableNameFilter.class));
        cgConfig.setActiveDatasource(datasource);
        cgConfig.setProperties(ConvertUtils.convertObj(this.properties, MetaProperties.class));
        cgConfig.setFiles(ConvertUtils.convertObj(this.files, Files.class));

        return cgConfig;
    }
}
