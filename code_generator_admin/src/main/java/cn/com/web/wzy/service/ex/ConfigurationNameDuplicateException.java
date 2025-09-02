package cn.com.web.wzy.service.ex;

/**
 * 配置名已存在异常
 */
public class ConfigurationNameDuplicateException extends ServiceException{
    public ConfigurationNameDuplicateException() {
        super("配置名已存在异常");
    }

    public ConfigurationNameDuplicateException(String message) {
        super(message);
    }

    public ConfigurationNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public ConfigurationNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
