package cn.com.web.wzy.dao;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行sql
 */
@Repository
public class JdbcExecutor {

    /**
     * 查询操作
     * @param connection 连接对象
     * @param sql sql语句
     * @param params 参数
     * @return 查询结果
     * @throws SQLException sql异常
     */
    public List<Map<String, Object>> executeQuery(Connection connection, String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            // 执行查询
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // 处理结果集
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    resultList.add(row);
                }
            }
        }

        return resultList;
    }
}
