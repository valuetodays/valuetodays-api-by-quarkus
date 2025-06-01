package cn.valuetodays.module.codegenerator.advanced.config;

import cn.valuetodays.module.codegenerator.advanced.config.datasource.FileDatasource;
import cn.valuetodays.module.codegenerator.advanced.config.datasource.MysqlDatasource;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * @author lei.liu
 * @since 2019-10-09 18:01
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FileDatasource.class, name = "FILE"),
    @JsonSubTypes.Type(value = MysqlDatasource.class, name = "MYSQL")
})
@Data
public abstract class Datasource {
    private String id;
    private Type type;
    public enum Type {
        FILE,
        MYSQL
    }

}
