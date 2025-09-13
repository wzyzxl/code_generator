package cn.com.web.wzy.vo;

import cn.com.web.wzy.enums.DatabaseType;

import java.io.Serializable;
import java.util.Objects;

public class RequestVo implements Serializable {
    private String databaseId;                                      // 数据库连接id
    private DatabaseType databaseType;                              // 数据库类型
    private String host;                                            // 主机
    private Integer port;                                           // 端口
    private String databaseName;                                    // 数据库名
    private String username;                                        // 用户名
    private String password;                                        // 密码
    private boolean changeFlag;                                     // 数据库配置信息更改
    private String pattern;                                         // 模式
    private String table;                                           // 表名
    private String configName;                                      // 配置名称
    private boolean createEntity;                                   // 创建实体
    private boolean createRepository;                               // 创建数据库配置
    private boolean createController;                               // 创建controller
    private boolean createService;                                  // 创建service
    private boolean createSimpleSql;                                // 创建简单增删改查
    private String prePackageName;                                  // 前置包名
    private String fileName;                                        // 文件名

    public RequestVo() {
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(boolean changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public boolean isCreateEntity() {
        return createEntity;
    }

    public void setCreateEntity(boolean createEntity) {
        this.createEntity = createEntity;
    }

    public boolean isCreateRepository() {
        return createRepository;
    }

    public void setCreateRepository(boolean createRepository) {
        this.createRepository = createRepository;
    }

    public boolean isCreateController() {
        return createController;
    }

    public void setCreateController(boolean createController) {
        this.createController = createController;
    }

    public boolean isCreateService() {
        return createService;
    }

    public void setCreateService(boolean createService) {
        this.createService = createService;
    }

    public boolean isCreateSimpleSql() {
        return createSimpleSql;
    }

    public void setCreateSimpleSql(boolean createSimpleSql) {
        this.createSimpleSql = createSimpleSql;
    }

    public String getPrePackageName() {
        return prePackageName;
    }

    public void setPrePackageName(String prePackageName) {
        this.prePackageName = prePackageName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestVo requestVo = (RequestVo) o;
        return databaseType == requestVo.databaseType && Objects.equals(host, requestVo.host) && Objects.equals(port, requestVo.port) && Objects.equals(databaseName, requestVo.databaseName) && Objects.equals(username, requestVo.username) && Objects.equals(password, requestVo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseType, host, port, databaseName, username, password);
    }

    @Override
    public String toString() {
        return "RequestVo{" +
                "databaseId=" + databaseId +
                ", databaseType=" + databaseType +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", databaseName='" + databaseName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", changeFlag=" + changeFlag +
                ", pattern='" + pattern + '\'' +
                ", table='" + table + '\'' +
                ", configName='" + configName + '\'' +
                ", createEntity=" + createEntity +
                ", createRepository=" + createRepository +
                ", createController=" + createController +
                ", createService=" + createService +
                ", createSimpleSql=" + createSimpleSql +
                ", prePackageName='" + prePackageName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}