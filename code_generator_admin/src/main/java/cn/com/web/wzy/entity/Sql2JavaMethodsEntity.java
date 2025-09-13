package cn.com.web.wzy.entity;

import java.util.List;
import java.util.Objects;

/**
 * Sql转Java方法实体
 */
public class Sql2JavaMethodsEntity {
    private String methodName;                              // 方法名
    private String returnType;                              // 返回值类型
    private List<String> paramsType;                        // 参数类型
    private List<String> params;                            // 参数

    public Sql2JavaMethodsEntity() {
    }

    public Sql2JavaMethodsEntity(String methodName, String returnType, List<String> paramsType, List<String> params) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.paramsType = paramsType;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParamsType() {
        return paramsType;
    }

    public void setParamsType(List<String> paramsType) {
        this.paramsType = paramsType;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sql2JavaMethodsEntity that = (Sql2JavaMethodsEntity) o;
        return Objects.equals(methodName, that.methodName) && Objects.equals(returnType, that.returnType) && Objects.equals(paramsType, that.paramsType) && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, returnType, paramsType, params);
    }

    @Override
    public String toString() {
        return "Sql2JavaMethodsEntity{" +
                "methodName='" + methodName + '\'' +
                ", returnType='" + returnType + '\'' +
                ", paramsType=" + paramsType +
                ", params=" + params +
                '}';
    }
}
