package cn.valuetodays.module.codegenerator.advanced.config.datasource;

import cn.valuetodays.module.codegenerator.advanced.config.Datasource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileDatasource extends Datasource {
    private boolean preferUseResource;
    private String settingResource;
    private String settingFile;

    public FileDatasource() {
        setType(Type.FILE);
    }

}
