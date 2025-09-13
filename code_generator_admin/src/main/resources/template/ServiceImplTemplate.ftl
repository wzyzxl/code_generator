<#if packageName?? && packageName != "">
${packageName}
</#if>

<#list importClassPackage as importClassPackageItem>
    <#if importClassPackageItem?? && importClassPackageItem != "">
import ${importClassPackageItem};
    </#if>
</#list>

public interface ${className} implements ${interfaceName}｛

｝