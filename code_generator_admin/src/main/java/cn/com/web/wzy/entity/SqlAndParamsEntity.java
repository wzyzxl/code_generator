package cn.com.web.wzy.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 动态sql语句以及对应参数
 */
public class SqlAndParamsEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sql;
    private List<Object> params;

    public SqlAndParamsEntity() {
        this.params = new ArrayList<Object>();
    }

    public SqlAndParamsEntity(String sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlAndParamsEntity that = (SqlAndParamsEntity) o;
        return Objects.equals(sql, that.sql) && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, params);
    }

    @Override
    public String toString() {
        return "SqlAndParamsEntity{" +
                "sql='" + sql + '\'' +
                ", params=" + params +
                '}';
    }
}
