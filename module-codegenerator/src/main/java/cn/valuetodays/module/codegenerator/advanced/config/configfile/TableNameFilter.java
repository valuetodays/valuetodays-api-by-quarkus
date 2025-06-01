package cn.valuetodays.module.codegenerator.advanced.config.configfile;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.db.nameconverter.ConverterContext;
import cn.valuetodays.module.codegenerator.db.nameconverter.TableName2ClassNameConvertFactory;
import cn.valuetodays.module.codegenerator.db.nameconverter.TableName2ClassNameConverter;
import cn.valuetodays.module.codegenerator.util.StringExUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableNameFilter implements AfterPropertiesSet {
    private String prefix;
    private String suffix;
    private List<String> includes;
    private List<String> excludes;
    private String tableName2ClassNameConverter;
    @Setter(AccessLevel.NONE)
    private TableName2ClassNameConverter tableName2ClassNameConverterObj;
    @Setter(AccessLevel.NONE)
    private ConverterContext converterContext;

    @Override
    public void afterPropertiesSet() {
        includes = includes.stream().filter(StringExUtil::isNotBlank).toList();
        excludes = excludes.stream().filter(StringExUtil::isNotBlank).toList();
        tableName2ClassNameConverterObj =
            TableName2ClassNameConvertFactory.of(tableName2ClassNameConverter);
        converterContext = new ConverterContext();
        converterContext.setTableNamePrefix(prefix);
    }
}
