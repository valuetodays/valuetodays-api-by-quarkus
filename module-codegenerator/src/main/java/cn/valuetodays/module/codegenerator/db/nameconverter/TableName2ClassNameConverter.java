package cn.valuetodays.module.codegenerator.db.nameconverter;

/**
 * @author lei.liu
 * @since 2018-09-06 17:32:08
 */
public abstract class TableName2ClassNameConverter {

    public String doConvert(String tableName, ConverterContext context) {
        if (tableName == null) {
            throw new NullPointerException();
        }
        return convert(tableName, context);
    }

    protected abstract String convert(String tableName, ConverterContext context);
}
