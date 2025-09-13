package cn.com.web.wzy.entity;

import java.util.List;
import java.util.Objects;

/**
 * sql转xml方法实体
 */
public class Sql2XmlMethodEntity {
    private String type;
    private String id;
    private Boolean containResultType = false;
    private String resultType;
    private List<String> fields;
    private List<String> smallHumpFields;
    private String tableName;
    private List<String> conditions;
    private List<String> smallHumpConditions;

    public Sql2XmlMethodEntity() {
    }

    public Sql2XmlMethodEntity(String type, String id, Boolean containResultType, String resultType, List<String> fields, List<String> smallHumpFields, String tableName, List<String> conditions, List<String> smallHumpConditions) {
        this.type = type;
        this.id = id;
        this.containResultType = containResultType;
        this.resultType = resultType;
        this.fields = fields;
        this.smallHumpFields = smallHumpFields;
        this.tableName = tableName;
        this.conditions = conditions;
        this.smallHumpConditions = smallHumpConditions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getContainResultType() {
        return containResultType;
    }

    public void setContainResultType(Boolean containResultType) {
        this.containResultType = containResultType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getSmallHumpFields() {
        return smallHumpFields;
    }

    public void setSmallHumpFields(List<String> smallHumpFields) {
        this.smallHumpFields = smallHumpFields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<String> getSmallHumpConditions() {
        return smallHumpConditions;
    }

    public void setSmallHumpConditions(List<String> smallHumpConditions) {
        this.smallHumpConditions = smallHumpConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sql2XmlMethodEntity that = (Sql2XmlMethodEntity) o;
        return Objects.equals(type, that.type) && Objects.equals(id, that.id) && Objects.equals(containResultType, that.containResultType) && Objects.equals(resultType, that.resultType) && Objects.equals(fields, that.fields) && Objects.equals(smallHumpFields, that.smallHumpFields) && Objects.equals(tableName, that.tableName) && Objects.equals(conditions, that.conditions) && Objects.equals(smallHumpConditions, that.smallHumpConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, containResultType, resultType, fields, smallHumpFields, tableName, conditions, smallHumpConditions);
    }

    @Override
    public String toString() {
        return "Sql2XmlMethodEntity{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", containResultType=" + containResultType +
                ", resultType='" + resultType + '\'' +
                ", fields=" + fields +
                ", smallHumpFields=" + smallHumpFields +
                ", tableName='" + tableName + '\'' +
                ", conditions=" + conditions +
                ", smallHumpConditions=" + smallHumpConditions +
                '}';
    }
}
