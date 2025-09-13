package cn.com.web.wzy.utils;

import cn.com.web.wzy.constant.Constant;
import cn.com.web.wzy.entity.DatabaseConnectionEntity;
import cn.com.web.wzy.vo.RequestVo;
import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数据库操作公共类
 */
public class DatabaseUtils {

    // 记录连接池和数据源信息
    private static final Map<String, DatabaseConnectionEntity> dataSourceMap = new ConcurrentHashMap<>();

    static {
        ThreadPoolUtils.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(Constant.DELETE_EXPIRED_DATABASE_RESOURCES);
                    removeExpiredDataSources();
                } catch (InterruptedException e) {
                    Constant.LOG.warn("数据库连接清理线程被中断");
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    Constant.LOG.error("删除过期数据库连接资源出错: {}", e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(RequestVo requestVo) throws SQLException {
        // 验证请求参数
        validateRequest(requestVo);

        // 生成连接的唯一标识
        String connectionKey = generateConnectionKey(requestVo);

        DatabaseConnectionEntity entity = dataSourceMap.get(connectionKey);

        // 如果存在现有连接且匹配，尝试复用
        if (isConnectionMatch(entity, requestVo)) {
            entity.setLinkTime(System.currentTimeMillis());

            // 获取连接是否还在
            DruidDataSource dataSource = entity.getDataSource();

            if (dataSource == null) {
                removeDataSourceByKey(connectionKey);
                return createNewConnection(requestVo, connectionKey);
            }

            // 验证连接是否仍然有效
            Connection connection = entity.getDataSource().getConnection();
            if(validateConnection(connection))
                return connection;
            else
                removeDataSourceByKey(connectionKey);
        }

        // 创建新连接
        return createNewConnection(requestVo, connectionKey);
    }

    /**
     * 生成连接的唯一标识键
     */
    private static String generateConnectionKey(RequestVo requestVo) {
        return String.format("%s:%s:%s:%s:%s",
                requestVo.getDatabaseType(),
                requestVo.getHost(),
                requestVo.getPort(),
                requestVo.getDatabaseName(),
                requestVo.getUsername());
    }

    /**
     * 创建新的数据库连接
     */
    private static Connection createNewConnection(RequestVo requestVo, String connectionKey) throws SQLException {
        DruidDataSource dataSource = createDataSource(requestVo);

        try {
            Connection connection = dataSource.getConnection();
            if (validateConnection(connection)) {
                DatabaseConnectionEntity entity = new DatabaseConnectionEntity(
                        connectionKey, requestVo.getDatabaseType(), requestVo.getHost(),
                        requestVo.getPort(), requestVo.getDatabaseName(),
                        requestVo.getUsername(), requestVo.getPassword(),
                        dataSource, System.currentTimeMillis()
                );

                dataSourceMap.put(connectionKey, entity);

                requestVo.setDatabaseId(connectionKey);
                return connection;
            } else {
                closeDataSourceSilently(dataSource);
                throw new SQLException("数据库连接验证失败");
            }
        } catch (SQLException e) {
            closeDataSourceSilently(dataSource);
            Constant.LOG.error("创建数据库连接失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 根据key移除数据源
     */
    private static void removeDataSourceByKey(String connectionKey) {
        try {
            DatabaseConnectionEntity entity = dataSourceMap.remove(connectionKey);
            if (entity != null) {
                DruidDataSource dataSource = entity.getDataSource();
                if (dataSource != null && !dataSource.isClosed()) {
                    dataSource.close();
                }
            }
        } catch (Exception e) {
            Constant.LOG.error("移除数据源时出错: {}", e.getMessage(), e);
        }
    }

    /**
     * 验证请求参数有效性
     */
    private static void validateRequest(RequestVo requestVo) {
        Objects.requireNonNull(requestVo, "请求参数不能为空");
        Objects.requireNonNull(requestVo.getDatabaseType(), "数据库类型不能为空");
        Objects.requireNonNull(requestVo.getHost(), "主机地址不能为空");
        Objects.requireNonNull(requestVo.getDatabaseName(), "数据库名不能为空");
        Objects.requireNonNull(requestVo.getUsername(), "用户名不能为空");
        Objects.requireNonNull(requestVo.getPassword(), "密码不能为空");

        if (requestVo.getPort() <= 0 || requestVo.getPort() > 65535) {
            throw new IllegalArgumentException("端口号无效: " + requestVo.getPort());
        }
    }

    /**
     * 验证连接是否有效
     */
    private static boolean validateConnection(Connection connection) {
        if (connection == null) return false;

        try {
            return connection.isValid(3); // 3秒超时验证
        } catch (SQLException e) {
            Constant.LOG.warn("连接验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查连接信息是否匹配
     */
    private static boolean isConnectionMatch(DatabaseConnectionEntity entity, RequestVo requestVo) {
        return entity != null &&
                entity.getDatabaseType() == requestVo.getDatabaseType() &&
                Objects.equals(entity.getHost(), requestVo.getHost()) &&
                entity.getPort() == requestVo.getPort() &&
                Objects.equals(entity.getDatabaseName(), requestVo.getDatabaseName()) &&
                Objects.equals(entity.getUsername(), requestVo.getUsername()) &&
                Objects.equals(entity.getPassword(), requestVo.getPassword());
    }

    /**
     * 移除过期的数据源
     */
    private static void removeExpiredDataSources() {
        long currentTime = System.currentTimeMillis();
        List<String> expiredKeys = new CopyOnWriteArrayList<>();

        // 找出所有过期的连接
        dataSourceMap.forEach((key, entity) -> {
            if (currentTime - entity.getLinkTime() > Constant.CONNECTION_EXPIRATION_TIME) {
                expiredKeys.add(key);
            }
        });

        // 移除过期连接
        expiredKeys.forEach(DatabaseUtils::removeDataSourceByKey);

        if (!expiredKeys.isEmpty()) {
            Constant.LOG.info("已清理 {} 个过期数据库连接", expiredKeys.size());
        }
    }

    /**
     * 创建Druid数据源
     */
    private static DruidDataSource createDataSource(RequestVo requestVo) {
        DruidDataSource dataSource = new DruidDataSource();
        String url = requestVo.getDatabaseType().buildJdbcUrl(requestVo);

        dataSource.setUrl(url);
        dataSource.setUsername(requestVo.getUsername());
        dataSource.setPassword(requestVo.getPassword());

        // 优化连接池配置
        configureConnectionPool(dataSource);

        return dataSource;
    }

    /**
     * 配置连接池参数
     */
    private static void configureConnectionPool(DruidDataSource dataSource) {
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(5); // 根据实际需求调整
        dataSource.setMaxWait(10000); // 减少等待时间
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true); // 获取连接时验证
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(false); // 减少资源占用

        // 连接泄漏检测配置
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(120); // 2分钟
        dataSource.setLogAbandoned(true);
    }

    /**
     * 安静关闭数据源
     */
    private static void closeDataSourceSilently(DruidDataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                Constant.LOG.warn("关闭数据源时出错: {}", e.getMessage());
            }
        }
    }

    /**
     * 获取当前活跃连接数
     */
    public static int getActiveConnectionCount() {
        return dataSourceMap.size();
    }

    /**
     * 清理所有数据库连接
     */
    public static void shutdown() {
        dataSourceMap.keySet().forEach(DatabaseUtils::removeDataSourceByKey);
        Constant.LOG.info("已清理所有数据库连接");
    }
}