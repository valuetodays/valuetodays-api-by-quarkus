package cn.valuetodays.module.codegenerator.db.nameconverter;

/**
 * @author lei.liu
 * @since 2018-10-02 22:33:23
 */
public class Empty2ClassNameConverter extends TableName2ClassNameConverter {

    @Override
    protected String convert(String tableName, ConverterContext context) {
        return "";
    }

}
