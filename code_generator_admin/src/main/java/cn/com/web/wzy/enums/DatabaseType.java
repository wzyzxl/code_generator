package cn.com.web.wzy.enums;

import cn.com.web.wzy.service.ex.DatabaseTypeNotFoundException;
import cn.com.web.wzy.vo.RequestVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库类型映射
 */
public enum DatabaseType {
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL");

    private final String value;
    DatabaseType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    /**
     * 获取所有数据库对应类型
     * @return 返回数据库类型列表
     */
    public static List<Map<String, String>> getValues() {
        List<Map<String, String>> list = new ArrayList<>();
        for (DatabaseType item : DatabaseType.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("name", item.getValue());
            map.put("value", item.toString());
            list.add(map);
        }
        return list;
    }

    /**
     * 构建jdbcUrl
     * @param requestVo 请求
     * @return jdbcUrl
     */
    public String buildJdbcUrl(RequestVo requestVo) {
        return switch (this) {
            case MYSQL ->
                    String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC",
                            requestVo.getHost(), requestVo.getPort(), requestVo.getDatabaseName());
            case POSTGRESQL -> String.format("jdbc:postgresql://%s:%d/%s",
                    requestVo.getHost(), requestVo.getPort(), requestVo.getDatabaseName());
            default -> throw new DatabaseTypeNotFoundException();
        };
    }
}
