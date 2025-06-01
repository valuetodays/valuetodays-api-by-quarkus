package cn.valuetodays.module.codegenerator.db.nameconverter;

public class OriginWithoutPrefixTableName2ClassNameConverter
    extends TableName2ClassNameConverter {

    @Override
    protected String convert(String tableName, ConverterContext context) {
        return tableName.substring(context.getTableNamePrefix().length());
    }
}
