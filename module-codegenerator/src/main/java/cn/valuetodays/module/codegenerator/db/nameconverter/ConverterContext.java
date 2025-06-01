package cn.valuetodays.module.codegenerator.db.nameconverter;

/**
 * @author lei.liu
 * @since 2019-03-26 17:53
 */
public class ConverterContext {
    private String tableNamePrefix;

    public String getTableNamePrefix() {
        return tableNamePrefix;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }
}
