package cn.com.web.wzy.template;

import cn.com.web.wzy.entity.Sql2JavaMethodsEntity;
import cn.com.web.wzy.entity.Sql2XmlMethodEntity;
import cn.com.web.wzy.utils.StringUtils;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class XmlMapperGenerator extends BaseTemplateGenerator {
    private List<Sql2XmlMethodEntity> methods;

    private String fileEntity;  // 文件实体名称
    private String fileMapper;  // 文件对应Mapper名称

    /**
     * 代码生成
     *
     * @param requestVo   前端
     * @param sqlDataInfo sql信息
     * @param saveToFile  是否保存成文件
     * @return 文件路径或者代码
     */
    public String codeGenerator(RequestVo requestVo, List<Map<String, Object>> sqlDataInfo, boolean saveToFile) {
        // 初始化
        super.initAttribute(requestVo, "xml");
        this.methods = new ArrayList<>();

        // 重构包名
        fileEntity = StringUtils.toPascalCase(requestVo.getTable()) + "Entity";
        fileMapper = StringUtils.toPascalCase(requestVo.getTable()) + "Mapper";
        super.dataModel.put("packageName",
                requestVo.getPrePackageName().isEmpty() ? fileMapper : requestVo.getPrePackageName() + ".mapper." + fileMapper);

        // 根据是否创建简单增删改查添加方法
        if (requestVo.isCreateSimpleSql()) {
            creationMethods(requestVo, sqlDataInfo);
        }
        super.dataModel.put("methods", this.methods);

        // 保存
        return super.saveFile(requestVo, super.dataModel, "XmlMapperTemplate.ftl", saveToFile);
    }

    /**
     * 创建方法
     *
     * @param requestVo   前端请求
     * @param sqlDataInfo sql信息
     */
    private void creationMethods(RequestVo requestVo, List<Map<String, Object>> sqlDataInfo) {
        // 获取所有字段
        List<String> fields = new ArrayList<>(),
                smallHumpFields = new ArrayList<>();
        getAllFields(sqlDataInfo, fields, smallHumpFields);

        // 简单的增加数据方法
        methods.add(new Sql2XmlMethodEntity("insert", "insert", false, null, fields, smallHumpFields, requestVo.getTable(), null, null));

        // 简单的修改方法
        methods.add(new Sql2XmlMethodEntity("update", "update", false, null, fields, smallHumpFields, requestVo.getTable(), List.of("id"), List.of("id")));

        // 简单的查询方法
        methods.add(new Sql2XmlMethodEntity("select", "queryAll", true,
                requestVo.getPrePackageName().isEmpty() ? fileEntity : requestVo.getPrePackageName() + ".entity." + fileEntity, fields, smallHumpFields, requestVo.getTable(), null, null));

        // 按照id 查询
        methods.add(new Sql2XmlMethodEntity("select", "queryById", true,
                requestVo.getPrePackageName().isEmpty() ? fileEntity : requestVo.getPrePackageName() + ".entity." + fileEntity,
                fields, smallHumpFields,
                requestVo.getTable(),
                List.of("id"), List.of("id")));

        // 简单删除方法
        methods.add(new Sql2XmlMethodEntity("delete", "deleteById", false, null, fields, smallHumpFields, requestVo.getTable(), List.of("id"), List.of("id")));
    }

    /**
     * 获取所有字段
     * @param sqlDataInfo sql信息
     * @param fields 所有字段
     * @param smallHumpFields 所有小驼峰字段
     */
    private void getAllFields(List<Map<String, Object>> sqlDataInfo, List<String> fields, List<String> smallHumpFields) {
        for (Map<String, Object> map : sqlDataInfo) {
            fields.add((String) map.get("name"));
            smallHumpFields.add(StringUtils.toCamelCase((String) map.get("name")));
        }
    }
}
