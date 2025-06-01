package cn.valuetodays.module.codegenerator.db;


import cn.valuetodays.module.codegenerator.advanced.config.configfile.TableNameFilter;
import cn.valuetodays.module.codegenerator.pojo.TableClass;

import java.util.List;

/**
 * @since 2016-02-26 23:15
 */
public interface IDatabase {

    List<TableClass> getAllTableClasses(TableNameFilter tableNameFilter);

    void paddingAllTableInfo(List<TableClass> list);
}
