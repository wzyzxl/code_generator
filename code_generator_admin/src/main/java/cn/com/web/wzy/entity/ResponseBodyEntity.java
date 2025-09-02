package cn.com.web.wzy.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ResponseBodyEntity<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;
    private Long timestamp;


    // 私有化构造函数，避免直接实例化
    private ResponseBodyEntity() {
        this.timestamp = System.currentTimeMillis();
    }

    private ResponseBodyEntity(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // 成功响应 - 无数据
    public static ResponseBodyEntity<Void> ok() {
        return new ResponseBodyEntity<>(200, "操作成功", null);
    }

    // 成功响应 - 带数据
    public static <T> ResponseBodyEntity<T> ok(T data) {
        return new ResponseBodyEntity<>(200, "操作成功", data);
    }

    // 成功响应 - 自定义消息和数据
    public static <T> ResponseBodyEntity<T> ok(String message, T data) {
        return new ResponseBodyEntity<>(200, message, data);
    }

    // 成功响应 - 自定义状态码、消息和数据
    public static <T> ResponseBodyEntity<T> ok(Integer code, String message, T data) {
        return new ResponseBodyEntity<>(code, message, data);
    }

    // 错误响应 - 默认错误
    public static ResponseBodyEntity<Void> error() {
        return new ResponseBodyEntity<>(500, "操作失败", null);
    }

    // 错误响应 - 自定义错误消息
    public static ResponseBodyEntity<Void> error(String message) {
        return new ResponseBodyEntity<>(500, message, null);
    }

    // 错误响应 - 自定义状态码和错误消息
    public static ResponseBodyEntity<Void> error(Integer code, String message) {
        return new ResponseBodyEntity<>(code, message, null);
    }

    // 错误相应，自定义所有信息
    public static <T> ResponseBodyEntity<T> error(Integer code, String message, T data) {
        return new ResponseBodyEntity<>(code, message, data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBodyEntity<?> that = (ResponseBodyEntity<?>) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data, timestamp);
    }

    @Override
    public String toString() {
        return "ResponseBodyEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}