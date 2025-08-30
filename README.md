# 轻量型代码生成器（Code Generator）
一个基于 Spring Boot + Vue3 + MyBatis 开发的轻量型代码生成工具，旨在帮助开发者减少重复编码工作（如实体类、Mapper、基础 Controller 生成），通过可视化配置快速生成符合规范的代码，支持本地打包下载。
## 🌟 核心特性
- 支持Mysql与PostgreSQL
- 支持自定义包名、选择生成文件类型（实体类 / MyBatis XML/Mapper/Controller）
- Vue3 可视化界面，包含数据库连接测试、表结构预览、生成进度展示
- 生成代码符合 Java 命名规范，支持字段驼峰映射（如 user_name → userName）
- 生成代码按包名目录结构压缩为 ZIP，支持单文件预览与复制
## 📋 环境准备
|依赖项|       版本要求        |                       说明                        |
|:--:|:-----------------:|:-----------------------------------------------:|
|后端|      java 17      |                  推荐实用jdk7.0.12                  |
|构建工具|     Maven3.9      |             用于后端项目构建，推荐Maven3.9.11              |
|前端| node.js 22，npm 10 | 用于 Vue3 项目依赖安装，node.js推荐版本22.18.0，npm推荐版本10.9.3 |
|数据库| Mysql、PostgreSQL  |                   需提前创建目标数据库                    |
## 🚀 快速开始
### 1. 拉取代码
```bash
# 克隆仓库
git clone https://github.com/wzyzxl/code_generator.git
cd code_generator
```
### 2. 后端启动(Spring Boot)
#### 步骤1：创建默认表
将`./code_generato/sql`下的`.sql`文件导入到数据库中，或者通过下面sql语句创建数据表：
```sql
DROP TABLE IF EXISTS `database_config`;
CREATE TABLE `database_config` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名称',
  `database_type` enum('MYSQL','POSTGRESQL') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库类型，MYSQL或POSTGRESQL',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主机地址',
  `port` int NOT NULL COMMENT '端口号',
  `database_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库名称',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者标识（V1版本不涉及，可按需删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（V1版本不涉及，可按需删除）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（V1版本不涉及，可按需删除）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志，0表示未删除，1表示删除，（V1版本不涉及，可按需删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_name_unique` (`config_name`) COMMENT '唯一配置名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用于存储页面中数据库的相关配置，方便后续直接连接相关配置数据库，而不用重复连接';
```
#### 步骤2：配置数据库连接
进入后端目录 `./code_generator/code_generator_admin/src/main/resources`，修改 `application.yml` 中的数据库配置（本地开发环境）：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver  # MySQL 8.0+ 驱动；MySQL 5.7 用 com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/你的数据库名?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: 你的数据库用户名
    password: 你的数据库密码
    type: com.alibaba.druid.pool.DruidDataSource  # Druid 连接池
```
#### 步骤3：构建并启动
```bash
# 进入后端目录
cd code_generator_admin
#  Maven 构建
mvn clean package -DskipTests
# 启动 Spring Boot 应用（或直接在 IDE 中运行 CodeGeneratorApplication.java）
java -jar target/code-generator.jar
```
### 3. 前端启动(vue3)
#### 步骤 1：安装依赖
```bash
# 进入前端目录
cd code_generator_ui
# 安装 npm 依赖
npm install
```
#### 步骤 2：启动开发环境
```bash
npm run dev
```
前端服务默认启动在 [http://localhost:5173](http://localhost:5173)，打开浏览器访问即可进入操作界面。
## 📖 使用指南
### 第一步：配置数据库连接
- 选择数据库类型（MySQL/PostgreSQL），输入 IP、端口、数据库名、用户名、密码
- 点击「测试连接」验证有效性，验证通过后点击「保存配置」可以进行保存
### 第二步：选择表结构
- 连接成功后，点击数据库模式以及数据表可以显示模式和表
- 可通过搜索框模糊筛选表，选中表后可以通过输入框后小按钮呈现基本信息
### 第三步：设置生成参数
- 输入自定义包名（如 cn.edu.project），生成代码将按「包名 + 子模块」组织（如 cn.edu.project.entity）
- 勾选需生成的文件类型（必选：实体类 / MyBatis XML；可选：Mapper / 基础 Controller）
- 勾选「生成简单增删改查」，MyBatis XML 中将自动添加 selectByPrimaryKey/insert 等方法
### 第四步：生成与下载
- 点击「生成代码」，页面展示实时进度（如「生成实体类：2/3」）
  生成完成后，可点击「预览」查看单文件代码（支持语法高亮），或点击「打包下载」获取 ZIP 压缩包
## 📌 扩展计划（V2 版本）
□支持 Swagger 注解自动生成（Controller 接口添加 @Api/@ApiOperation）<br>
□支持 Lombok 注解配置（实体类生成 @Data/@NoArgsConstructor）<br>
□自定义代码模板上传（允许用户替换默认 Freemarker 模板）<br>
□历史生成记录保存（记录过往配置，支持重新生成）
## 📄 许可证
本项目基于**MIT许可证**开源，详情见**LICENSE**文件。