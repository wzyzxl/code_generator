<#if packageName?? && packageName != "">
${packageName}
</#if>

import org.apache.ibatis.annotations.Mapper;
<#list importClassPackage as importClass>
    <#if importClass?? && importClass != "">
import ${importClass};
    </#if>
</#list>

/**
* ${tableName}映射类
* @author ${author}
* @date ${createDate}
*/
@Mapper
public interface ${className} {
<#list methods as method>
    <#if method??>
    ${method.returnType} ${method.methodName}(<#if method.paramsType??><#list method.paramsType as paramType>${paramType} ${method.params[paramType_index]}<#if paramType_has_next>, </#if></#list></#if>);

    </#if>
</#list>
}