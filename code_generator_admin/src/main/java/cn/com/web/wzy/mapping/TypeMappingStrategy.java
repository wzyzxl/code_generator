package cn.com.web.wzy.mapping;

/**
 * 定义映射策略接口
 */
public interface TypeMappingStrategy {
    /**
     * 获取数据类型映射关系
     * @param dbType 数据库数据类型
     * @param defaultType 默认数据类型
     * @return Java映射类型
     */
    Database2JavaMapping getTypeMapping(String dbType, String defaultType);
}
