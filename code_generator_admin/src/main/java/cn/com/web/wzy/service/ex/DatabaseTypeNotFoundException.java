package cn.com.web.wzy.service.ex;

/**
 * 数据库类型不存在异常
 */
public class DatabaseTypeNotFoundException extends ServiceException{
    public DatabaseTypeNotFoundException() {
        super("数据库类型不存在异常");
    }

    public DatabaseTypeNotFoundException(String message) {
        super(message);
    }

    public DatabaseTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public DatabaseTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
