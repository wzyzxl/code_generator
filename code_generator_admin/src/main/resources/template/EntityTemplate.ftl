<#if packageName?? && packageName != "">
${packageName}
</#if>

import java.io.Serial;
import java.io.Serializable;

<#list importClassPackage as importClassPackageItem>
    <#if importClassPackageItem?? && importClassPackageItem != "">
import ${importClassPackageItem};
    </#if>
</#list>

/**
 * ${tableName}实体类
 * @author ${author}
 * @date ${createDate}
 */
public class ${className} implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // 字段信息
    <#list fields as field>
    private ${field.type} ${field.lowHumpName};
    </#list>

    <#list fields as field>
    public ${field.type} get${field.greatHumpName}() {
        return this.${field.lowHumpName};
    }

    public void set${field.greatHumpName}(${field.type} ${field.lowHumpName}) {
        this.${field.lowHumpName} = ${field.lowHumpName};
    }
    </#list>

    // toString方法
    @Override
    public String toString() {
    return "${className}{" +
    <#list fields as field>
        <#if field_index < fields?size - 1>
        "${field.lowHumpName}=" + this.${field.lowHumpName} + ", " +
        <#else>
        "${field.lowHumpName}=" + this.${field.lowHumpName}
        </#if>
    </#list>
    + "}";
    }
}
