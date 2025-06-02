package cn.valuetodays.api2.web.extend;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.MySQLDialect;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-02
 */
public class NoEscapeMySQLDialect extends MySQLDialect {
    public NoEscapeMySQLDialect() {
        super(DatabaseVersion.make(8));
    }

    @Override
    public boolean isNoBackslashEscapesEnabled() {
        return true;
    }
}
