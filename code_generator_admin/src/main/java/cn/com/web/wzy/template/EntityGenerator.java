package cn.com.web.wzy.template;

import cn.com.web.wzy.mapping.Database2JavaMapping;
import cn.com.web.wzy.mapping.MysqlMapping;
import cn.com.web.wzy.mapping.PostgresqlMapping;
import cn.com.web.wzy.service.ex.DatabaseTypeNotFoundException;
import cn.com.web.wzy.service.ex.FileGenerationErrorException;
import cn.com.web.wzy.utils.StringUtils;
import cn.com.web.wzy.vo.RequestVo;

import cn.com.web.wzy.entity.DatabaseFieldInfoEntity;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Java实体类生成器
 */
@Component
public class EntityGenerator extends BaseTemplateGenerator{
    // 字段
    private List<DatabaseFieldInfoEntity> fields;

    /**
     * 生成实体
     *
     * @param requestVo   请求
     * @param sqlDataInfo 数据表信息
     * @param saveToFile  是否保存到文件
     * @return 保存到文件时返回文件路径，否则返回生成的文本内容
     */
    public String generateEntity(RequestVo requestVo, List<Map<String, Object>> sqlDataInfo, boolean saveToFile) {
        // 初始化
        super.initAttribute(requestVo, "entity");
        this.fields = new ArrayList<>();

        // 完善数据模型
        createDateModel(requestVo, sqlDataInfo);

        // 保存
        return super.saveFile(requestVo, dataModel, "EntityTemplate.ftl", saveToFile);
    }

    /**
     * 创建数据模型
     *
     * @param requestVo   前端
     * @param sqlDataInfo 数据表信息
     */
    private void createDateModel(RequestVo requestVo, List<Map<String, Object>> sqlDataInfo) {
        // 根据数据库表字段类型进行处理
        for (Map<String, Object> sqlData : sqlDataInfo) {
            String name = sqlData.get("name") == null ? null : sqlData.get("name").toString();
            String type = sqlData.get("type") == null ? null : sqlData.get("type").toString();
            String length = sqlData.get("length") == null ? null : sqlData.get("length").toString();

            if (name == null || type == null) {
                throw new FileGenerationErrorException("字段名或者类型存在问题");
            }

            // 重构类型
            type = length == null ? type.toUpperCase() : type.toLowerCase() + "_" + length;
            Database2JavaMapping database2JavaMapping = null;                                   // 将数据库中类型转为Java类型
            DatabaseFieldInfoEntity databaseFieldInfoEntity = new DatabaseFieldInfoEntity();     // 模版映射字段实体
            switch (requestVo.getDatabaseType()) {
                case POSTGRESQL:
                    database2JavaMapping = new PostgresqlMapping().getTypeMapping(type.toUpperCase());
                    type = database2JavaMapping.getJavaType();
                    if (database2JavaMapping.getImportPackage() != null && !database2JavaMapping.getImportPackage().isEmpty()) {
                        super.importClassPackage.add(database2JavaMapping.getImportPackage());
                    }
                    break;
                case MYSQL:
                    database2JavaMapping = new MysqlMapping().getTypeMapping(type.toUpperCase());
                    type = database2JavaMapping.getJavaType();
                    if (database2JavaMapping.getImportPackage() != null && !database2JavaMapping.getImportPackage().isEmpty()) {
                        super.importClassPackage.add(database2JavaMapping.getImportPackage());
                    }
                    break;
                default:
                    throw new DatabaseTypeNotFoundException();
            }
            databaseFieldInfoEntity.setType(type);
            databaseFieldInfoEntity.setGreatHumpName(StringUtils.toPascalCase(name));
            databaseFieldInfoEntity.setLowHumpName(StringUtils.toCamelCase(name));
            fields.add(databaseFieldInfoEntity);
        }

        super.dataModel.put("fields", fields);
    }
}