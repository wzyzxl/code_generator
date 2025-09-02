package cn.com.web.wzy.mapping;

import cn.com.web.wzy.enums.DatabaseType;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库映射基类
 */
public abstract class BaseDatabaseMapping implements TypeMappingStrategy{
    /**
     * 存储映射关系
     */
    private final Map<String, Database2JavaMapping> TYPE_MAPPINGS = new HashMap<>();


    /**
     * 添加映射关系
     * @param dbType ｛String｝ 数据库类型，特殊类型用类型_长度表示
     * @param javaType {String} Java类型
     * @param className {Class} Java类
     * @param importPackage {String} 包名
     */
    protected void addMapping(String dbType, String javaType, Class<?> className, String importPackage) {
        TYPE_MAPPINGS.put(dbType.toUpperCase(), new Database2JavaMapping(javaType, className, importPackage));
    }

    /**
     * 判断是不是所支持的数据库
     * @param dbType 数据库类型
     * @return 是否支持
     */
    public abstract boolean supports(DatabaseType dbType);

    @Override
    public Database2JavaMapping getTypeMapping(String dbType, String defaultType) {
        if(dbType == null) return TYPE_MAPPINGS.get(defaultType);
        Database2JavaMapping result = TYPE_MAPPINGS.get(dbType.toUpperCase());
        return result == null ? TYPE_MAPPINGS.get(defaultType) : result;
    }
}
