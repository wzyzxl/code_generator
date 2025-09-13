package cn.com.web.wzy.service.impl;

import cn.com.web.wzy.builder.DatabaseSqlBuilder;
import cn.com.web.wzy.constant.Constant;
import cn.com.web.wzy.dao.JdbcExecutor;
import cn.com.web.wzy.entity.SqlAndParamsEntity;
import cn.com.web.wzy.enums.DatabaseType;
import cn.com.web.wzy.service.CodeService;
import cn.com.web.wzy.service.ex.ConnectionInformationErrorException;
import cn.com.web.wzy.service.ex.FileGenerationErrorException;
import cn.com.web.wzy.service.ex.FileNotFoundException;
import cn.com.web.wzy.template.*;
import cn.com.web.wzy.utils.DatabaseUtils;
import cn.com.web.wzy.utils.FileUtils;
import cn.com.web.wzy.utils.StringUtils;
import cn.com.web.wzy.utils.ThreadPoolUtils;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Service
public class ICodeServiceImpl implements CodeService {
    // sql构建工具
    private final DatabaseSqlBuilder databaseSqlBuilder;

    // sql执行工具
    private final JdbcExecutor jdbcExecutor;

    // 实体类构建工具
    private final EntityGenerator entityGenerator;

    // Java的Mapper层构建
    private final JavaMapperGenerator javaMapperGenerator;

    // xml的mapper层构建
    private final XmlMapperGenerator xmlMapperGenerator;

    // controller
    private final ControllerTemplateGenerator controllerTemplateGenerator;

    // service
    private final ServiceTemplateGenerator serviceTemplateGenerator;

    // service 实现层
    private final ServiceImplTemplateGenerator serviceImplTemplateGenerator;

    // 文件路径以及文件服务器url
    @Value("${code-generator.output-path}")
    private String filePath;
    @Value("${file-server.url}")
    private String fileServerUrl;

    public ICodeServiceImpl(DatabaseSqlBuilder databaseSqlBuilder, JdbcExecutor jdbcExecutor, EntityGenerator entityGenerator, JavaMapperGenerator javaMapperGenerator, XmlMapperGenerator xmlMapperGenerator, ControllerTemplateGenerator controllerTemplateGenerator, ServiceTemplateGenerator serviceTemplateGenerator, ServiceImplTemplateGenerator serviceImplTemplateGenerator) {
        this.databaseSqlBuilder = databaseSqlBuilder;
        this.jdbcExecutor = jdbcExecutor;
        this.entityGenerator = entityGenerator;
        this.javaMapperGenerator = javaMapperGenerator;
        this.xmlMapperGenerator = xmlMapperGenerator;
        this.controllerTemplateGenerator = controllerTemplateGenerator;
        this.serviceTemplateGenerator = serviceTemplateGenerator;
        this.serviceImplTemplateGenerator = serviceImplTemplateGenerator;
    }

    @Override
    public List<String> filenames(RequestVo requestVo) {
        try {
            // 判断数据合格性
            verifyData(requestVo);

            // 获取文件名
            List<String> list = new LinkedList<>();

            // 文件名前缀
            String prefix = StringUtils.toPascalCase(requestVo.getTable());

            // 实体类
            if (requestVo.isCreateEntity()) {
                list.add(prefix + "Entity.java");
            }

            // mapper
            if (requestVo.isCreateRepository()) {
                list.add(prefix + "Mapper.java");
                list.add(prefix + "Mapper.xml");
            }

            // service层
            if (requestVo.isCreateService()) {
                list.add(prefix + "Service.java");
                list.add("I" + prefix + "ServiceImpl.java");
            }

            // controller层
            if (requestVo.isCreateController()) {
                list.add(prefix + "Controller.java");
            }
            return list;
        } catch (ConnectionInformationErrorException e) {
            throw new ConnectionInformationErrorException(e.getMessage());
        }
    }

    @Override
    public Map<String, String> content(RequestVo requestVo) {
        Map<String, String> result = new HashMap<>();

        // 基本验证
        verifyData(requestVo);
        // 验证文件名
        if (requestVo.getFileName() == null || requestVo.getFileName().trim().isEmpty())
            throw new FileNotFoundException("文件名不能为空");

        // 获取所有文件名
        List<String> list = filenames(requestVo);

        // 判断文件名是否存在
        if (list == null || !list.contains(requestVo.getFileName())) throw new FileNotFoundException();

        try (Connection connection = DatabaseUtils.getConnection(requestVo)) {
            // 获取数据表信息
            SqlAndParamsEntity sqlAndParamsEntity = databaseSqlBuilder.buildQueryFields(requestVo);
            List<Map<String, Object>> sqlDataInfo = jdbcExecutor.executeQuery(connection, sqlAndParamsEntity.getSql(), sqlAndParamsEntity.getParams());

            if (sqlDataInfo.isEmpty()) {
                throw new FileNotFoundException("文件信息有误，请核对表名或者模式名是否有误。");
            }

            // 生成代码
            result.put("code", createCodeFile(requestVo, sqlDataInfo, false));

        } catch (SQLException e) {
            throw new ConnectionInformationErrorException(e.getMessage());
        }

        return result;
    }

    @Override
    public Map<String, String> download(RequestVo requestVo) {
        try (Connection connection = DatabaseUtils.getConnection(requestVo)) {
            // 获取文件名
            List<String> filenames = filenames(requestVo);

            // 获取数据表信息
            SqlAndParamsEntity sqlAndParamsEntity = databaseSqlBuilder.buildQueryFields(requestVo);
            List<Map<String, Object>> sqlDataInfo = jdbcExecutor.executeQuery(connection, sqlAndParamsEntity.getSql(), sqlAndParamsEntity.getParams());

            if (sqlDataInfo.isEmpty()) {
                throw new FileNotFoundException("文件信息有误，请核对表名或者模式名是否有误。");
            }

            // 记录所有生成的文件路径
            List<String> filePaths = new ArrayList<>();

            // 遍历生成文件
            for (String filename : filenames) {
                // 生成文件
                RequestVo cloneRequestVo = SerializationUtils.clone(requestVo);
                cloneRequestVo.setFileName(filename);
                String filePath = createCodeFile(cloneRequestVo, sqlDataInfo, true);
                filePaths.add(filePath);
            }

            // 按照路径将文件放入压缩包
            String zipPath = FileUtils.compressFilesToZip(filePaths, Path.of(filePath, requestVo.getTable(), requestVo.getTable() + ".zip").toString());

            // 开辟一条新线程，等待5分钟后删除文件
            ThreadPoolUtils.execute(() -> {
                try {
                    Thread.sleep(Constant.DELETE_FILE);
                    // 删除
                    FileUtils.deleteFiles(List.of(zipPath));
                } catch (InterruptedException e) {
                    Constant.LOG.error("定时器出错：{}", e.getMessage());
                }
            });
            return Map.of("url", fileServerUrl + "/" + requestVo.getTable() + "/" + requestVo.getTable() + ".zip", "message", "请尽快保存该文件，文件将于5分钟后自动删除。");
        } catch (SQLException e) {
            throw new ConnectionInformationErrorException(e.getMessage());
        } catch (IOException e) {
            throw new FileGenerationErrorException(e.getMessage());
        }
    }

    /**
     * 验证数据有效性
     *
     * @param requestVo 前端数据
     */
    private void verifyData(RequestVo requestVo) {
        if (requestVo.getDatabaseType() == null) {
            throw new ConnectionInformationErrorException("数据库类型不能为空");
        } else if (requestVo.getHost() == null || requestVo.getHost().trim().isEmpty()) {
            throw new ConnectionInformationErrorException("主机地址不能为空");
        } else if (requestVo.getPort() == null) {
            throw new ConnectionInformationErrorException("端口不能为空");
        } else if (requestVo.getPort() < 1 || requestVo.getPort() > 65535) {
            throw new ConnectionInformationErrorException("端口号需要介于1到65535之间");
        } else if (requestVo.getUsername() == null || requestVo.getUsername().trim().isEmpty()) {
            throw new ConnectionInformationErrorException("用户名不能为空");
        } else if (requestVo.getPassword() == null || requestVo.getPassword().trim().isEmpty()) {
            throw new ConnectionInformationErrorException("密码不能为空");
        } else if (requestVo.getDatabaseType() == DatabaseType.POSTGRESQL && (requestVo.getPattern() == null || requestVo.getPattern().trim().isEmpty())) {
            throw new ConnectionInformationErrorException("模式不能为空");
        } else if (requestVo.getTable() == null || requestVo.getTable().trim().isEmpty()) {
            throw new ConnectionInformationErrorException("表名不能为空");
        }
    }

    /**
     * 构建文件代码
     *
     * @param requestVo   前端请求
     * @param sqlDataInfo sql信息
     * @param saveFile    是否保存为文件
     * @return 代码或者文件路径
     */
    private String createCodeFile(RequestVo requestVo, List<Map<String, Object>> sqlDataInfo, Boolean saveFile) {
        // 生成文件代码
        if (requestVo.getFileName().contains("Entity")) {
            // 实体类
            return entityGenerator.generateEntity(requestVo, sqlDataInfo, saveFile);
        } else if (requestVo.getFileName().contains("Mapper.java")) {
            // Mapper层Java代码
            return javaMapperGenerator.codeGenerator(requestVo, saveFile);
        } else if (requestVo.getFileName().contains("Mapper.xml")) {
            // Mapper层xml
            return xmlMapperGenerator.codeGenerator(requestVo, sqlDataInfo, saveFile);
        } else if (requestVo.getFileName().contains("Controller")) {
            // Controller层
            return controllerTemplateGenerator.generateController(requestVo, saveFile);
        } else if (requestVo.getFileName().contains("Service.java")) {
            // Service层
            return serviceTemplateGenerator.generateController(requestVo, saveFile);
        } else if (requestVo.getFileName().contains("ServiceImpl.java")) {
            // Service实现层
            return serviceImplTemplateGenerator.generateController(requestVo, saveFile);
        } else {
            throw new FileNotFoundException();
        }
    }
}
