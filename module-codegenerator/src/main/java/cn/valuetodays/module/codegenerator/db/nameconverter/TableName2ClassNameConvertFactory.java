package cn.valuetodays.module.codegenerator.db.nameconverter;

import cn.valuetodays.module.codegenerator.util.StringExUtil;

/**
 * @author lei.liu
 * @since 2019-03-26 16:15
 */
public class TableName2ClassNameConvertFactory {

    public static final String CAMEL_WITHOUT_PREFIX = "camelWithoutPrefix";
    public static final String ORIGIN_WITHOUT_PREFIX = "originWithoutPrefix";
    public static final String EMPTY = "empty";

    private TableName2ClassNameConvertFactory() {
    }

    public static TableName2ClassNameConverter of(String name) {
        if (StringExUtil.equals("origin", name)) {
            return new OriginTableName2ClassNameConverter();
        } else if (StringExUtil.equals("camel", name)) {
            return new CamelTableName2ClassNameConverter();
        } else if (StringExUtil.equals(CAMEL_WITHOUT_PREFIX, name)) {
            return new CamelWithoutPrefixTableName2ClassNameConverter();
        } else if (StringExUtil.equals(ORIGIN_WITHOUT_PREFIX, name)) {
            return new OriginWithoutPrefixTableName2ClassNameConverter();
        } else if (StringExUtil.equals(EMPTY, name)) {
            return new Empty2ClassNameConverter();
        }

        throw new IllegalArgumentException("unknown converter name: " + name);
    }
}
