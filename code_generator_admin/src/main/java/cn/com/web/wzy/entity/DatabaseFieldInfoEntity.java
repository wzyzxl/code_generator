package cn.com.web.wzy.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class DatabaseFieldInfoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String greatHumpName;
    private String lowHumpName;
    private String type;

    public DatabaseFieldInfoEntity() {
    }

    public DatabaseFieldInfoEntity(String greatHumpName, String lowHumpName, String type) {
        this.greatHumpName = greatHumpName;
        this.lowHumpName = lowHumpName;
        this.type = type;
    }

    public String getGreatHumpName() {
        return greatHumpName;
    }

    public void setGreatHumpName(String greatHumpName) {
        this.greatHumpName = greatHumpName;
    }

    public String getLowHumpName() {
        return lowHumpName;
    }

    public void setLowHumpName(String lowHumpName) {
        this.lowHumpName = lowHumpName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseFieldInfoEntity that = (DatabaseFieldInfoEntity) o;
        return Objects.equals(greatHumpName, that.greatHumpName) && Objects.equals(lowHumpName, that.lowHumpName) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(greatHumpName, lowHumpName, type);
    }

    @Override
    public String toString() {
        return "DatabaseFieldInfoEntity{" +
                "greatHumpName='" + greatHumpName + '\'' +
                ", lowHumpName='" + lowHumpName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
