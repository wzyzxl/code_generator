package cn.com.web.wzy.mapping;

import cn.com.web.wzy.enums.DatabaseType;

import java.math.BigDecimal;
import java.time.*;

public class PostgresqlMapping extends BaseDatabaseMapping {

    {
        // 数字类型
        addMapping("SMALLINT", "Short", Short.class, null);
        addMapping("INT2", "Short", Short.class, null);
        addMapping("INTEGER", "Integer", Integer.class, null);
        addMapping("INT", "Integer", Integer.class, null);
        addMapping("INT4", "Integer", Integer.class, null);
        addMapping("BIGINT", "Long", Long.class, null);
        addMapping("INT8", "Long", Long.class, null);
        addMapping("REAL", "Float", Float.class, null);
        addMapping("FLOAT4", "Float", Float.class, null);
        addMapping("DOUBLE", "Double", Double.class, null);
        addMapping("FLOAT8", "Double", Double.class, null);
        addMapping("NUMERIC", "BigDecimal", BigDecimal.class, "java.math.BigDecimal");
        addMapping("DECIMAL", "BigDecimal", BigDecimal.class, "java.math.BigDecimal");
        addMapping("MONEY", "BigDecimal", BigDecimal.class, "java.math.BigDecimal");
        addMapping("BOOLEAN", "Boolean", Boolean.class, null);

        // 日期时间类型
        addMapping("DATE", "LocalDate", LocalDate.class, "java.time.LocalDate");
        addMapping("TIME", "LocalTime", LocalTime.class, "java.time.LocalTime");
        addMapping("TIMETZ", "OffsetTime", OffsetTime.class, "java.time.OffsetTime");
        addMapping("TIMESTAMP", "LocalDateTime", LocalDateTime.class, "java.time.LocalDateTime");
        addMapping("TIMESTAMPTZ", "OffsetDateTime", OffsetDateTime.class, "java.time.OffsetDateTime");
        addMapping("INTERVAL", "Duration", Duration.class, "java.time.Duration");

        // 字符串类型
        addMapping("CHAR", "String", String.class, null);
        addMapping("VARCHAR", "String", String.class, null);
        addMapping("TEXT", "String", String.class, null);
        addMapping("UUID", "UUID", java.util.UUID.class, "java.util.UUID");
        addMapping("ENUM", "String", String.class, null);

        // 二进制类型
        addMapping("BYTEA", "byte[]", byte[].class, "");

        // JSON类型
        addMapping("JSON", "String", String.class, null);
        addMapping("JSONB", "String", String.class, null);

        // 网络地址类型
        addMapping("INET", "String", String.class, null);
        addMapping("CIDR", "String", String.class, null);

        // 地理
        addMapping("POINT", "String", String.class, null);
        addMapping("LINE", "String", String.class, null);
        addMapping("LTREE", "String", String.class, null);
    }

    public Database2JavaMapping getTypeMapping(String dbType) {
        return super.getTypeMapping(dbType, "VARCHAR");
    }

    @Override
    public boolean supports(DatabaseType dbType) {
        return DatabaseType.POSTGRESQL.equals(dbType);
    }
}
