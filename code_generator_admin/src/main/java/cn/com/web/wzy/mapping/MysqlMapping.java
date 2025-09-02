package cn.com.web.wzy.mapping;

import cn.com.web.wzy.enums.DatabaseType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * mysql映射
 */
public class MysqlMapping extends BaseDatabaseMapping {
    {
        // 数字类型
        addMapping("BIT", "Integer", Integer.class, null);
        addMapping("BIT_1", "Boolean", Boolean.class, null);
        addMapping("TINYINT", "Byte", Byte.class, null);
        addMapping("TINYINT_1",  "Boolean", Boolean.class, null);
        addMapping("SMALLINT", "Short", Short.class, null);
        addMapping("MEDIUMINT", "Integer", Integer.class, null);
        addMapping("INT", "Integer", Integer.class, null);
        addMapping("INTEGER", "Integer", Integer.class, null);
        addMapping("BIGINT", "Long", Long.class, null);
        addMapping("FLOAT", "Float", Float.class, null);
        addMapping("DOUBLE", "Double", Double.class, null);
        addMapping("DECIMAL", "BigDecimal", BigDecimal.class, "java.math.BigDecimal");
        addMapping("NUMERIC", "BigDecimal", BigDecimal.class, "java.math.BigDecimal");

        // 无符号类型映射示例
        addMapping("TINYINT UNSIGNED", "Short", Short.class, null);
        addMapping("SMALLINT UNSIGNED", "Integer", Integer.class, null);
        addMapping("INT UNSIGNED", "Long", Long.class, null);
        addMapping("BIGINT UNSIGNED", "BigInteger", BigInteger.class, "java..BigInteger");

        // 日期时间类型
        addMapping("DATE", "LocalDate", LocalDate.class, "java.time.LocalDate");
        addMapping("TIME", "LocalTime", LocalTime.class, "java.time.LocalTime");
        addMapping("DATETIME", "LocalDateTime", LocalDateTime.class, "java.time.LocalDateTime");
        addMapping("TIMESTAMP", "LocalDateTime", LocalDateTime.class, "java.time.LocalDateTime");
        addMapping("YEAR", "Integer", Integer.class, null);

        // 字符串类型
        addMapping("CHAR", "String", String.class, null);
        addMapping("VARCHAR", "String", String.class, null);
        addMapping("TINYTEXT", "String", String.class, null);
        addMapping("TEXT", "String", String.class, null);
        addMapping("MEDIUMTEXT", "String", String.class, null);
        addMapping("LONGTEXT", "String", String.class, null);
        addMapping("ENUM", "String", String.class, null);
        addMapping("SET", "String", String.class, null);
        addMapping("JSON", "String", String.class, null);

        // 二进制类型
        addMapping("BINARY", "byte[]", byte[].class, "");
        addMapping("VARBINARY", "byte[]", byte[].class, "");
        addMapping("TINYBLOB", "byte[]", byte[].class, "");
        addMapping("BLOB", "byte[]", byte[].class, "");
        addMapping("MEDIUMBLOB", "byte[]", byte[].class, "");
        addMapping("LONGBLOB", "byte[]", byte[].class, "");
    }

    public Database2JavaMapping getTypeMapping(String dbType) {
        return super.getTypeMapping(dbType, "VARCHAR");
    }

    @Override
    public boolean supports(DatabaseType dbType) {
        return DatabaseType.MYSQL.equals(dbType);
    }
}
