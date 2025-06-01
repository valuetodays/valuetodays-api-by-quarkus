package cn.valuetodays.module.codegenerator.db;

import cn.valuetodays.module.codegenerator.advanced.config.configfile.TableNameFilter;
import cn.valuetodays.module.codegenerator.pojo.Field;
import cn.valuetodays.module.codegenerator.pojo.TableClass;
import cn.valuetodays.module.codegenerator.util.StringExUtil;
import cn.vt.exception.CommonException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class DBMySql implements IDatabase {

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Map<String, TypeForMultiLanguage> mysqlType2JavaType = new HashMap<>();
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    static {
        mysqlType2JavaType.put("bit", TypeForMultiLanguage.builder().java("Boolean").go("bool").build());
        mysqlType2JavaType.put("int", TypeForMultiLanguage.builder().java("Integer").go("int32").build());
        mysqlType2JavaType.put("tinyint", TypeForMultiLanguage.builder().java("Integer").go("int32").build());
        mysqlType2JavaType.put("double", TypeForMultiLanguage.builder().java("Double").go("float64").build());
        mysqlType2JavaType.put("float", TypeForMultiLanguage.builder().java("Double").go("float64").build());
        mysqlType2JavaType.put("decimal", TypeForMultiLanguage.builder().java("BigDecimal").go("decimal.Decimal").build());
        mysqlType2JavaType.put("text", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("tinytext", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("mediumtext", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("longtext", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("varchar", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("char", TypeForMultiLanguage.builder().java("String").go("string").build());
        mysqlType2JavaType.put("bigint", TypeForMultiLanguage.builder().java("Long").go("int64").build());
        mysqlType2JavaType.put("datetime", TypeForMultiLanguage.builder().java("LocalDateTime").go("*time.Time").build());
        mysqlType2JavaType.put("date", TypeForMultiLanguage.builder().java("LocalDate").go("*time.Time").build());
        mysqlType2JavaType.put("time", TypeForMultiLanguage.builder().java("LocalTime").go("*time.Time").build());
        mysqlType2JavaType.put("timestamp", TypeForMultiLanguage.builder().java("LocalDateTime").go("*time.Time").build());
        mysqlType2JavaType.put("enum", TypeForMultiLanguage.builder().java("enum").go("enum??").build());
        mysqlType2JavaType.put("json", TypeForMultiLanguage.builder().java("Object").go("any??").build());
    }

    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;

    public DBMySql(String url, String username, String password) {
        DB_URL = url;
        DB_USERNAME = username;
        DB_PASSWORD = password;
    }

    /**
     * 表名或字段名转成驼峰型，并去掉下划线_
     * 注意：表名会首字母大写，字段名不会
     * <p/>
     * 如  表名 USER_INFO  -->  UserInfo
     * 字段名 create_time --> createTime
     *
     * @param columnName column name
     */
    public static String columnOrTableName2JavaCamelStyleString(String columnName) {
        StringBuilder camelString = new StringBuilder(30);
        String[] split = columnName.toLowerCase().split("_");
        String s = null;
        for (int i = 0; i < split.length; i++) {
            s = split[i];
            if (i > 0) {
                s = StringExUtil.capitaliseFirst(s);
            }
            camelString.append(s);
        }

        return camelString.toString();
    }

    /**
     * @param mysqlType mysqlType
     * @param dataScale dataScale中若是有数据就一定会是数字，若此时dataScale大于0就说明要返回Double类型，
     *                  其它的就是简单的类型
     */
    public static TypeForMultiLanguage getMultiLanguageTypesByMysqlType(String mysqlType, String dataScale) {
        if (null == mysqlType || mysqlType.isEmpty()) {
            return TypeForMultiLanguage.builder().java(mysqlType).go(mysqlType).build();
        }
        // System.out.println(">>>" + mysqlType);
        if (dataScale == null || dataScale.isEmpty()) {
            if (mysqlType.contains("(")) {
                mysqlType = mysqlType.substring(0, mysqlType.indexOf("("));
            }
            return mysqlType2JavaType.get(mysqlType);
        }
        if (mysqlType.equalsIgnoreCase("NUMBER") && Integer.parseInt(dataScale) > 0) {
            return TypeForMultiLanguage.builder().java("Double").go("float64").build();
        }
        return mysqlType2JavaType.get(mysqlType);
    }

    /**
     * 获取所有表，放到TableClass类中
     */
    public List<TableClass> getAllTableClasses(TableNameFilter tableNameFilter) {
        List<TableClass> list = new ArrayList<>();
        TableClass tc = null;

        String prefix = StringExUtil.trimToEmpty(tableNameFilter.getPrefix());
        String suffix = StringExUtil.trimToEmpty(tableNameFilter.getSuffix());
        String nameLike = prefix + "%" + suffix;
        try {
            String sql = " SHOW TABLE STATUS  where Name like '" + nameLike + "'";
//            String sql = " SHOW TABLE STATUS  where Name like '" + nameLike.replace("_", "\\_") + "'";

            if (CollectionUtils.isNotEmpty(tableNameFilter.getIncludes())) {
                String includeStr =
                    tableNameFilter.getIncludes().stream()
                        .map(e -> "'" + e + "'")
                        .collect(Collectors.joining(", "));
                sql += " and Name in (" + includeStr + ")";
            }
            if (CollectionUtils.isNotEmpty(tableNameFilter.getExcludes())) {
                String excludeStr =
                    tableNameFilter.getExcludes().stream()
                        .map(e -> "'" + e + "'")
                        .collect(Collectors.joining(", "));
                sql += " and Name not in (" + excludeStr + ")";
            }
            log.info("sql: " + sql);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tc = new TableClass();
                tc.setTableName(resultSet.getString("Name"));

                tc.setComment(resultSet.getString("Comment"));
                list.add(tc);
            }

            closeQuietly(resultSet);
            closeQuietly(preparedStatement);
            closeQuietly(connection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    /**
     * 填充表信息
     * 填充表的字段集合
     * 转换成驼峰样式
     *
     * @param list list
     */
    public void paddingAllTableInfo(List<TableClass> list) {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            for (TableClass tc : list) {
                String sql = " SHOW FULL COLUMNS FROM " + tc.getTableName();
                //	System.out.println(sql);
                //Class.forName(DB_DRIVER);
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery(sql);
                List<Field> fields = new ArrayList<>();

                Field field = null;
                while (resultSet.next()) {
                    field = new Field();
                    field.setComment(resultSet.getString("comment"));
                    field.setColumnName(resultSet.getString("Field"));
                    field.setColumnType(resultSet.getString("type"));
                    field.setPrimaryKey("PRI".equals(resultSet.getString("Key")));
                    field.setNullable("YES".equals(resultSet.getString("Null")));
                    fields.add(field);
                }

                tc.setFields(fields);

                closeQuietly(resultSet);
                closeQuietly(preparedStatement);
            }
        } catch (SQLException e) {
            throw new CommonException(e);
        } finally {
            closeQuietly(connection);
        }
    }

    private void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                throw new CommonException(e);
            }
        }
    }

    @Data
    @Builder
    public static class TypeForMultiLanguage implements Serializable {
        private String java;
        private String go;
    }


}
