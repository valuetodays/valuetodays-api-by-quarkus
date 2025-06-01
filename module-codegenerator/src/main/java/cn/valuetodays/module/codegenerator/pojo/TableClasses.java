package cn.valuetodays.module.codegenerator.pojo;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import cn.valuetodays.module.codegenerator.db.IDatabase;
import cn.valuetodays.module.codegenerator.db.nameconverter.ConverterContext;
import cn.valuetodays.module.codegenerator.db.nameconverter.TableName2ClassNameConverter;
import lombok.Data;

import java.util.List;

@Data
public class TableClasses implements AfterPropertiesSet {
    private CgConfig cgConfig;
    private IDatabase database;
    private List<TableClass> finalTableClassList; // 最终结构表

    private List<TableClass> tableClassList; // 从配置文件中读取的结构列表，对应mysql的所有表

    @Override
    public void afterPropertiesSet() {
        final TableName2ClassNameConverter tableName2ClassNameConverter = cgConfig.getTableNameFilter().getTableName2ClassNameConverterObj();
        final ConverterContext converterContext = cgConfig.getTableNameFilter().getConverterContext();

        this.setFinalTableClassList(database.getAllTableClasses(cgConfig.getTableNameFilter()));

        finalTableClassList.forEach(e -> {
            String className = tableName2ClassNameConverter.doConvert(e.getTableName(), converterContext);
            e.setClassName(className);
        });

        // 如下方法做了如下工作：
        //  0. 获取每个表的每个字段的名字，类型和注释
        //  1. 将数据库中表字段的类型转换为相应的java类型
        //  2. 将表名和字段名改成相应的驼峰样式
        database.paddingAllTableInfo(finalTableClassList);

        finalTableClassList.forEach(e -> {
            e.setCgConfig(cgConfig);
            e.afterPropertiesSet();
        });
    }

}
