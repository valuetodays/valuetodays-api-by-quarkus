package cn.valuetodays.module.codegenerator.advanced.config.datasource;

import cn.valuetodays.module.codegenerator.advanced.config.Datasource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MysqlDatasource extends Datasource {
    private String url;
    private String username;
    private String password;

    public MysqlDatasource() {
        setType(Type.MYSQL);
    }

}
