package cn.com.web.wzy.template;

import cn.com.web.wzy.entity.Sql2JavaMethodsEntity;
import cn.com.web.wzy.utils.StringUtils;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaMapperGenerator extends BaseTemplateGenerator {
    private List<Sql2JavaMethodsEntity> methods;

    /**
     * 代码生成
     * @param requestVo 前端
     * @param saveToFile 是否保存成文件
     * @return 文件路径或者代码
     */
    public String codeGenerator(RequestVo requestVo, boolean saveToFile) {
        // 初始化
        super.initAttribute(requestVo, "mapper");
        this.methods = new ArrayList<>();

        // 根据是否创建简单增删改查添加方法
        if(requestVo.isCreateSimpleSql()) {
            creationMethods(requestVo);
        }
        super.dataModel.put("methods", this.methods);

        // 保存
        return super.saveFile(requestVo, super.dataModel, "JavaMapperTemplate.ftl", saveToFile);
    }

    /**
     * 创建方法
     * @param requestVo 前端请求
     */
    private void creationMethods(RequestVo requestVo) {
        // 文件对应实体
        String fileEntity = StringUtils.toPascalCase(requestVo.getTable()) + "Entity";
        // 文件对应实体参数名
        String fileParamName = StringUtils.toCamelCase(requestVo.getTable()) + "Entity";

        // 添加导入的包
        super.importClassPackage.add(requestVo.getPrePackageName().isEmpty() ? null : requestVo.getPrePackageName() + ".entity." + StringUtils.toPascalCase(fileParamName));

        // 简单的增加数据方法
        methods.add( new Sql2JavaMethodsEntity("insert", "Boolean",
                List.of(fileEntity), List.of(fileParamName)));

        // 简单的修改方法
        methods.add(new Sql2JavaMethodsEntity("update", "Boolean", List.of(fileEntity), List.of(fileParamName)));

        // 简单的查询方法
        methods.add(new Sql2JavaMethodsEntity("queryAll", fileEntity, null, null));

        // 按照id 查询
        methods.add(new Sql2JavaMethodsEntity("queryById", fileEntity, List.of("Long"), List.of("id")));

        // 简单删除方法
        methods.add(new Sql2JavaMethodsEntity("deleteById", "Boolean", List.of("Long"), List.of("id")));
    }
}