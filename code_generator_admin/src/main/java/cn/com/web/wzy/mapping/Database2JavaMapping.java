package cn.com.web.wzy.mapping;

import java.util.Objects;

/**
 * 数据库类型与Java类型映射
 */
public class Database2JavaMapping {
    private String javaType;
    private Class<?> className;
    private String importPackage;

    public Database2JavaMapping(String javaType, Class<?> className, String importPackage) {
        this.javaType = javaType;
        this.className = className;
        this.importPackage = importPackage;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public String getImportPackage() {
        return importPackage;
    }

    public void setImportPackage(String importPackage) {
        this.importPackage = importPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database2JavaMapping that = (Database2JavaMapping) o;
        return Objects.equals(javaType, that.javaType) && Objects.equals(className, that.className) && Objects.equals(importPackage, that.importPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(javaType, className, importPackage);
    }

    @Override
    public String toString() {
        return "BaseDatabaseMapping{" +
                "javaType='" + javaType + '\'' +
                ", className=" + className +
                ", importPackage='" + importPackage + '\'' +
                '}';
    }
}
