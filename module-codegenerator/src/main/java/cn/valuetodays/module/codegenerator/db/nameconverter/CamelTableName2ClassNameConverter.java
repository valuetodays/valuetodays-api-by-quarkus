package cn.valuetodays.module.codegenerator.db.nameconverter;

import cn.valuetodays.module.codegenerator.util.StringExUtil;
import org.springframework.util.StringUtils;

/**
 * @author lei.liu
 * @since 2019-03-26 16:20
 */
public class CamelTableName2ClassNameConverter extends TableName2ClassNameConverter {
    @Override
    protected String convert(String tableName, ConverterContext context) {
        if (!tableName.startsWith("_")) {
            tableName += "_";
        }
        StringBuilder camelString = new StringBuilder();
        String[] split = StringUtils.tokenizeToStringArray(tableName.toLowerCase(), "_");
        String s = null;
        for (int i = 0; i < split.length; i++) {
            s = split[i];
//            if (i > 0) {
            s = StringExUtil.capitaliseFirst(s);
//            }
            camelString.append(s);
        }

        return camelString.toString();
    }
}
