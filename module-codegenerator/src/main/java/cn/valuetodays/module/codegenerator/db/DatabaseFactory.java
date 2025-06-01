package cn.valuetodays.module.codegenerator.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2016-02-26 23:10
 */
public class DatabaseFactory {
    public static final String DB_MYSQL = "MYSQL";
    private static List<String> dbTypes = new ArrayList<>();

    static {
        try {
            Class.forName(DBMySql.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbTypes.add(DB_MYSQL);
    }

    private DatabaseFactory() {
    }

    public static IDatabase createDatabase(String dbType, String url, String username, String password) {
        if (!dbTypes.contains(dbType)) {
            throw new RuntimeException("unknown database type:" + dbType);
        }
        if (DB_MYSQL.equals(dbType)) {
            return new DBMySql(url, username, password);
        }
        throw new RuntimeException("unknown database type:" + dbType);
    }
}
