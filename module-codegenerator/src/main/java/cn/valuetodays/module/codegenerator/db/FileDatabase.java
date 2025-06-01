package cn.valuetodays.module.codegenerator.db;

import cn.valuetodays.module.codegenerator.advanced.config.configfile.TableNameFilter;
import cn.valuetodays.module.codegenerator.pojo.TableClass;
import cn.valuetodays.module.codegenerator.pojo.TableClasses;
import cn.valuetodays.module.codegenerator.util.StringExUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu
 * @since 2019-10-12 17:33
 */
public class FileDatabase implements IDatabase {

    private final TableClasses tableClasses;

    public FileDatabase(TableClasses tableClasses) {
        this.tableClasses = tableClasses;
    }

    @Override
    public List<TableClass> getAllTableClasses(TableNameFilter tableNameFilter) {
        String prefix = StringExUtil.trimToEmpty(tableNameFilter.getPrefix());
        String suffix = StringExUtil.trimToEmpty(tableNameFilter.getSuffix());

        List<TableClass> tableClassesFinalList = new ArrayList<>();
        List<TableClass> tableClassCandidateList = tableClasses.getTableClassList();
        for (TableClass tableClassCandidate : tableClassCandidateList) {
            String tableName = tableClassCandidate.getTableName();
            boolean match = true;
            if (StringExUtil.isNotBlank(prefix)) {
                match = StringUtils.startsWithIgnoreCase(tableName, prefix);
            }
            if (match && StringExUtil.isNotBlank(suffix)) {
                match = StringUtils.endsWithIgnoreCase(tableName, suffix);
            }
            if (!match && CollectionUtils.isNotEmpty(tableNameFilter.getIncludes())) {
                match = tableNameFilter.getIncludes().contains(tableName);
            }
            if (match && CollectionUtils.isNotEmpty(tableNameFilter.getExcludes())) {
                match = !tableNameFilter.getExcludes().contains(tableName);
            }

            if (match) {
                tableClassesFinalList.add(tableClassCandidate);
            }
        }

        return tableClassesFinalList;
    }

    @Override
    public void paddingAllTableInfo(List<TableClass> list) {
        // do nothing
    }

}
