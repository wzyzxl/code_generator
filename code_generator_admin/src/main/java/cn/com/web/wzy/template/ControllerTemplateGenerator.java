package cn.com.web.wzy.template;

import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Component;

@Component
public class ControllerTemplateGenerator extends BaseTemplateGenerator{
    /**
     * 生成实体
     *
     * @param requestVo   请求
     * @param saveToFile  是否保存到文件
     * @return 保存到文件时返回文件路径，否则返回生成的文本内容
     */
    public String generateController(RequestVo requestVo, boolean saveToFile) {
        // 初始化
        super.initAttribute(requestVo, "controller");

        // 保存
        return super.saveFile(requestVo, dataModel, "ControllerTemplate.ftl", saveToFile);
    }
}
