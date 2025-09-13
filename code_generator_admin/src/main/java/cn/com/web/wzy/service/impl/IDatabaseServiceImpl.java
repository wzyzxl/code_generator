package cn.com.web.wzy.service.impl;

import cn.com.web.wzy.builder.DatabaseSqlBuilder;
import cn.com.web.wzy.dao.JdbcExecutor;
import cn.com.web.wzy.entity.SqlAndParamsEntity;
import cn.com.web.wzy.enums.DatabaseType;
import cn.com.web.wzy.mapper.DatabaseConfigMapper;
import cn.com.web.wzy.service.DatabaseService;
import cn.com.web.wzy.service.ex.ConfigurationNameDuplicateException;
import cn.com.web.wzy.service.ex.ConnectionInformationErrorException;
import cn.com.web.wzy.service.ex.WriteInformationErrorException;
import cn.com.web.wzy.utils.DatabaseUtils;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IDatabaseServiceImpl implements DatabaseService {

    // sql构建工具
    private final DatabaseSqlBuilder databaseSqlBuilder;

    // sql执行工具
    private final JdbcExecutor jdbcExecutor;

    // 数据库
    private final DatabaseConfigMapper databaseConfigMapper;

    public IDatabaseServiceImpl(DatabaseSqlBuilder databaseSqlBuilder, JdbcExecutor jdbcExecutor,
                                DatabaseConfigMapper databaseConfigMapper) {
        this.databaseSqlBuilder = databaseSqlBuilder;
        this.jdbcExecutor = jdbcExecutor;
        this.databaseConfigMapper = databaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> types() {
        return DatabaseType.getValues();
    }

    @Override
    public Map<String, Object> connections(RequestVo requestVo) {
        try(Connection conn = DatabaseUtils.getConnection(requestVo)) {
            Map<String, Object> result = new HashMap<>();
            if (conn != null) {
                result.put("state", true);
            } else {
                result.put("state", false);
            }
            result.put("databaseId", requestVo.getDatabaseId());
            return result;
        } catch (Exception e) {
            // 连接出错
            throw new ConnectionInformationErrorException();
        }
    }

    @Override
    public List<String> patterns(RequestVo requestVo) {
        try(Connection connection = DatabaseUtils.getConnection(requestVo)) {

            // 生成sql和参数
            SqlAndParamsEntity sqlAndParamsEntity = databaseSqlBuilder.buildQueryPattern(requestVo);

            return queryAndConvertToList(connection, sqlAndParamsEntity, "pattern_name");
        } catch (SQLException e) {
            throw new ConnectionInformationErrorException();
        }
    }

    @Override
    public List<String> tables(RequestVo requestVo) {
        try(Connection connection = DatabaseUtils.getConnection(requestVo)) {

            // 生成sql和参数
            SqlAndParamsEntity sqlAndParamsEntity = databaseSqlBuilder.buildQueryTable(requestVo);

            return queryAndConvertToList(connection, sqlAndParamsEntity, "table_name");
        } catch (SQLException e) {
            throw new ConnectionInformationErrorException();
        }
    }

    @Override
    public Map<String, Object> save(RequestVo requestVo) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 判断配置名是否已经存在
            int flag = databaseConfigMapper.queryByConfigName(requestVo);
            if (flag > 0) {
                throw new ConfigurationNameDuplicateException();
            }
            // 不存在就添加
            result.put("state", databaseConfigMapper.insertDatabaseConfig(requestVo));
        } catch (Exception e) {
            throw new WriteInformationErrorException("写入信息错误：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询sql并把结果转成List<String>
     *
     * @param connection         连接对象
     * @param sqlAndParamsEntity sql以及参数
     * @param convertName        查询转换字段名
     * @return 目标列表
     * @throws SQLException sql查询异常
     */
    private List<String> queryAndConvertToList(Connection connection, SqlAndParamsEntity sqlAndParamsEntity, String convertName) throws SQLException {
        if (connection == null || sqlAndParamsEntity == null || convertName == null) {
            return null;
        }
        // 执行查询
        List<Map<String, Object>> resultList = jdbcExecutor.executeQuery(connection,
                sqlAndParamsEntity.getSql(), sqlAndParamsEntity.getParams());

        // 返回值
        List<String> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            // 遍历map，查找相同的key（不区分大小写）
            for (String key : map.keySet()) {
                if (convertName.equalsIgnoreCase(key)) {
                    result.add((String) map.get(key));
                }
            }
        }
        return result;
    }
}
