package cn.com.web.wzy.builder;

import cn.com.web.wzy.entity.SqlAndParamsEntity;
import cn.com.web.wzy.service.ex.DatabaseTypeNotFoundException;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Component;

/**
 * 构建数据库连接相关sql
 */
@Component
public class DatabaseSqlBuilder {

    /**
     * 建立查询模式方法
     *
     * @param requestVo 前端数据
     * @return sql以及参数
     */
    public SqlAndParamsEntity buildQueryPattern(RequestVo requestVo) {
        SqlAndParamsEntity sqlAndParamsEntity = new SqlAndParamsEntity();
        // 根据数据库类型处理
        switch (requestVo.getDatabaseType()) {
            case POSTGRESQL:
                // 设置sql
                sqlAndParamsEntity.setSql(getPatternForPostgreSql().toString());
                // 参数
                sqlAndParamsEntity.getParams().add("%" + requestVo.getPattern() + "%");
                break;
            default:
                throw new DatabaseTypeNotFoundException();
        }

        return sqlAndParamsEntity;
    }

    /**
     * 查询表名
     *
     * @param requestVo 前端数据
     * @return 表名
     */
    public SqlAndParamsEntity buildQueryTable(RequestVo requestVo) {
        SqlAndParamsEntity sqlAndParamsEntity = new SqlAndParamsEntity();
        switch (requestVo.getDatabaseType()) {
            case POSTGRESQL:
                sqlAndParamsEntity.setSql(getTableForPostgreSql());
                sqlAndParamsEntity.getParams().add(requestVo.getPattern());
                sqlAndParamsEntity.getParams().add("%" + requestVo.getTable() + "%");
                break;
            case MYSQL:
                sqlAndParamsEntity.setSql(getTableForMySql());
                sqlAndParamsEntity.getParams().add(requestVo.getDatabaseName());
                sqlAndParamsEntity.getParams().add("%" + requestVo.getTable() + "%");
                break;
            default:
                throw new DatabaseTypeNotFoundException();
        }
        return sqlAndParamsEntity;
    }

    /**
     * 构建获取字段信息
     *
     * @param requestVo 前端数据
     * @return 返回值
     */
    public SqlAndParamsEntity buildQueryFields(RequestVo requestVo) {
        SqlAndParamsEntity sqlAndParamsEntity = new SqlAndParamsEntity();

        StringBuilder sql = new StringBuilder();

        switch (requestVo.getDatabaseType()) {
            case POSTGRESQL:
                // 所有长度均设为NULL
                sql.append("SELECT COLUMN_NAME AS name, DATA_TYPE AS type,  NULL length ");
                sql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
                sql.append("WHERE TABLE_CATALOG = ? AND TABLE_SCHEMA = ? AND TABLE_NAME = ?");

                // 参数
                sqlAndParamsEntity.getParams().add(requestVo.getDatabaseName());
                sqlAndParamsEntity.getParams().add(requestVo.getPattern());
                sqlAndParamsEntity.getParams().add(requestVo.getTable());
                break;
            case MYSQL:
                sql.append("SELECT COLUMN_NAME AS `name`, DATA_TYPE AS `type`, ");
                sql.append("CASE WHEN DATA_TYPE = 'enum' THEN NULL WHEN INSTR(COLUMN_TYPE, '(') > 0 THEN CASE WHEN SUBSTRING_INDEX(SUBSTRING(COLUMN_TYPE, INSTR(COLUMN_TYPE, '(') + 1), ')', 1) REGEXP '^[0-9]+$' THEN SUBSTRING_INDEX(SUBSTRING(COLUMN_TYPE, INSTR(COLUMN_TYPE, '(') + 1), ')', 1) ELSE NULL END ELSE NULL END AS `length` ");
                sql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
                sql.append("WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?");

                // 类型
                sqlAndParamsEntity.getParams().add(requestVo.getDatabaseName());
                sqlAndParamsEntity.getParams().add(requestVo.getTable());
                break;
            default:
                throw new DatabaseTypeNotFoundException();
        }

        // sql
        sqlAndParamsEntity.setSql(sql.toString());
        return sqlAndParamsEntity;
    }

    /**
     * 获取postgresql的模式
     *
     * @return 模式查询sql
     */
    private StringBuilder getPatternForPostgreSql() {
        StringBuilder sql = new StringBuilder("SELECT nspname AS pattern_name FROM pg_namespace WHERE ");
        //  排除核心系统模式
        sql.append("nspname NOT IN ('pg_catalog', 'information_schema', 'pg_toast') ");
        // 排除临时模式（以 pg_temp_ 开头）
        sql.append("AND NOT nspname LIKE 'pg_temp_%' ");
        // 排除临时 TOAST 模式（以 pg_toast_temp_ 开头）
        sql.append("AND NOT nspname LIKE 'pg_toast_temp_%' ");
        // 根据前端模式名称模糊查询
        sql.append("AND nspname LIKE ? ");
        return sql;
    }

    /**
     * postgresql获取表名称
     *
     * @return 表名
     */
    private String getTableForPostgreSql() {
        return "SELECT tablename AS table_name FROM pg_tables WHERE schemaname = ? AND tablename LIKE ?";
    }

    /**
     * 获取mysql表名
     *
     * @return 表名
     */
    private String getTableForMySql() {
        return "SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_name LIKE ?";
    }
}
