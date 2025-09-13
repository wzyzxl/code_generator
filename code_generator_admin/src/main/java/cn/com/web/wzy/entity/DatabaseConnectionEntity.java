package cn.com.web.wzy.entity;

import cn.com.web.wzy.enums.DatabaseType;
import com.alibaba.druid.pool.DruidDataSource;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Objects;

/**
 * 数据库连接成功实体
 */
public class DatabaseConnectionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String databaseId;                                      // 数据库连接id
    private DatabaseType databaseType;                              // 数据库类型
    private String host;                                            // 主机
    private int port;                                               // 端口
    private String databaseName;                                    // 数据库名
    private String username;                                        // 用户名
    private String password;                                        // 密码
    private DruidDataSource dataSource;                             // 连接资源
    private Long linkTime;                                          // 连接时长

    public DatabaseConnectionEntity() {
    }

    public DatabaseConnectionEntity(String databaseId, DatabaseType databaseType, String host, int port, String databaseName, String username, String password, DruidDataSource dataSource, Long linkTime) {
        this.databaseId = databaseId;
        this.databaseType = databaseType;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.dataSource = dataSource;
        this.linkTime = linkTime;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public DruidDataSource getDataSource() {
        return dataSource;
    }

    public Long getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Long linkTime) {
        this.linkTime = linkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseConnectionEntity that = (DatabaseConnectionEntity) o;
        return port == that.port && databaseType == that.databaseType && Objects.equals(host, that.host) && Objects.equals(databaseName, that.databaseName) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseType, host, port, databaseName, username, password);
    }

    @Override
    public String toString() {
        return "DatabaseConnectionEntity{" +
                "databaseId=" + databaseId +
                ", databaseType=" + databaseType +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", databaseName='" + databaseName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dataSource=" + dataSource +
                ", linkTime=" + linkTime +
                '}';
    }
}
