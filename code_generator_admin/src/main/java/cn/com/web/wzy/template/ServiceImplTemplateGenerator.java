package cn.com.web.wzy.template;

import cn.com.web.wzy.utils.StringUtils;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Component;

@Component
public class ServiceImplTemplateGenerator extends BaseTemplateGenerator {
    /**
     * 生成实体
     *
     * @param requestVo   请求
     * @param saveToFile  是否保存到文件
     * @return 保存到文件时返回文件路径，否则返回生成的文本内容
     */
    public String generateController(RequestVo requestVo, boolean saveToFile) {
        // 初始化
        super.initAttribute(requestVo, "service.impl");

        // service名称
        String serviceName = StringUtils.toPascalCase(requestVo.getTable()) + "Service";

        // 导入的包
        super.importClassPackage.add(requestVo.getPrePackageName().isEmpty() ? "" : requestVo.getPrePackageName() + ".service." + serviceName);

        // 接口名称
        super.dataModel.put("interfaceName", serviceName);

        // 保存
        return super.saveFile(requestVo, dataModel, "ServiceImplTemplate.ftl", saveToFile);
    }
}
