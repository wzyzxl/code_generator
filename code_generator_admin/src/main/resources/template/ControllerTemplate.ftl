<#if packageName?? && packageName != "">
${packageName}
</#if>

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("")
public class ${className} {

}