package cn.com.web.wzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成器应用启动类
 * 作为Spring Boot应用的入口点，负责启动整个后端服务
 */
@SpringBootApplication
@MapperScan("cn.com.web.wzy.mapper") // 扫描MyBatis的Mapper接口
public class CodeGeneratorApplication {

    // 使用非静态变量
    @Value("${server.port}")
    private Integer port;

    public static void main(String[] args) {
        // 启动Spring Boot应用并获取应用上下文
        var context = SpringApplication.run(CodeGeneratorApplication.class, args);

        // 从上下文中获取当前实例
        CodeGeneratorApplication app = context.getBean(CodeGeneratorApplication.class);

        System.out.println("代码生成器后端服务启动成功！访问地址: http://localhost:" + app.port);
    }
}