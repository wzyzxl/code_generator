package cn.com.web.wzy.template;

import cn.com.web.wzy.service.ex.FileGenerationErrorException;
import cn.com.web.wzy.vo.RequestVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 基础构造模版类
 */
@Component
public class BaseTemplateGenerator {

    // 导入的包
    protected Set<String> importClassPackage;

    // 输出路径
    @Value("${code-generator.output-path}")
    private String outputPath;

    @Value("${code-generator.author}")
    private String author;

    @Value("${code-generator.date-formatter}")
    private String dateFormatter;
    private String createDate;

    // 数据模型
    protected Map<String, Object> dataModel;

    // 配置
    protected Configuration configuration;

    @PostConstruct
    protected void init() {
        // Freemarker配置
        this.configuration = new Configuration(Configuration.VERSION_2_3_23);
        try {
            // 设置模板加载器 - 从classpath的template目录加载
            configuration.setClassLoaderForTemplateLoading(
                    getClass().getClassLoader(),
                    "template"
            );

            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("初始化Freemarker配置失败", e);
        }
    }

    /**
     * 初始化属性
     * @param requestVo 请求
     * @param packageType 包类型
     */
    protected void initAttribute(RequestVo requestVo, String packageType) {
        // 初始化
        this.importClassPackage = new LinkedHashSet<>();
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormatter));
        this.dataModel = new HashMap<>();
        // 设置公共模型参数
        createBaseDateModel(this.dataModel, requestVo, packageType);
    }

    /**
     * 创建公共数据模型
     * @param dataModel 数据模型
     * @param requestVo 前端
     * @param packageType 包类型（entity、mapper等）
     */
    protected void createBaseDateModel(Map<String, Object> dataModel, RequestVo requestVo, String packageType) {
        // 基本属性
        dataModel.put("packageName", requestVo.getPrePackageName().isEmpty() ? "" : "package " + requestVo.getPrePackageName() + "." + packageType + ";");
        dataModel.put("tableName", requestVo.getDatabaseName() + "." + requestVo.getTable());
        dataModel.put("author", author);
        dataModel.put("createDate", createDate);
        dataModel.put("className", requestVo.getFileName().split("\\.")[0]);
        dataModel.put("importClassPackage", importClassPackage);
    }

    /**
     * 保存操作
     * @param requestVo 前端请求
     * @param dataModel 数据模型
     * @param filename 文件名
     * @param saveToFile 是否保存成文件
     * @return 代码或者路径
     */
    protected String saveFile(RequestVo requestVo, Map<String, Object> dataModel, String filename, boolean saveToFile) {
        try{
            // 获取模板
            Template template = configuration.getTemplate(filename);

            if (saveToFile) {
                // 创建输出目录
                File outputFileDir = new File(Path.of(outputPath, requestVo.getTable()).toString());
                if (!outputFileDir.exists()) {
                    boolean isCreated = outputFileDir.mkdirs();
                    if (!isCreated)
                        throw new FileGenerationErrorException("文件路径创建失败，请确定是否存在创建文件的权限。");
                }

                // 创建输出文件
                File outputFile = new File(outputFileDir, requestVo.getFileName());

                // 生成文件
                try (Writer out = new FileWriter(outputFile)) {
                    template.process(dataModel, out);
                }

                return outputFile.getAbsolutePath();
            } else {
                // 不保存到文件，返回生成的文本内容
                try (StringWriter writer = new StringWriter()) {
                    template.process(dataModel, writer);
                    return writer.toString();
                }
            }
        }catch (IOException | TemplateException e) {
            throw new RuntimeException("生成实体类失败", e);
        }
    }
}
