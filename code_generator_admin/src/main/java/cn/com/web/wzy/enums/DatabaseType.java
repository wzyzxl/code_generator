package cn.com.web.wzy.enums;

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
    private DatabaseType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    /**
     * 获取所有数据库对应类型
     * @return 返回数据库类型列表
     */
    public static List<Map<String, Object>> getValues() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (DatabaseType item : DatabaseType.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", item.getValue());
            map.put("name", item);
        }
        return list;
    }
}
