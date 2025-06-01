package cn.valuetodays.module.codegenerator.db.nameconverter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lei.liu
 * @since 2019-03-26 17:52
 */
public class CamelWithoutPrefixTableName2ClassNameConverter extends TableName2ClassNameConverter {

    private CamelTableName2ClassNameConverter camelTableName2ClassNameConverter = new CamelTableName2ClassNameConverter();

    @Override
    protected String convert(String tableName, ConverterContext context) {
        String tableNamePrefix = context.getTableNamePrefix();
        String removedPrefixTableName = tableName.substring(tableNamePrefix.length());
        if (StringUtils.isEmpty(removedPrefixTableName)) {
            throw new IllegalArgumentException("illegal tablename [" + tableName + "] with prefix: " + tableNamePrefix);
        }
        return camelTableName2ClassNameConverter.doConvert(removedPrefixTableName, context);
    }
}
