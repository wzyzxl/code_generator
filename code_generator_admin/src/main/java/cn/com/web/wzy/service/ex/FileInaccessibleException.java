package cn.com.web.wzy.service.ex;

/**
 * 文件不可访问异常
 */
public class FileInaccessibleException extends ServiceException{
    public FileInaccessibleException() {
        super("文件不可访问");
    }

    public FileInaccessibleException(String message) {
        super(message);
    }

    public FileInaccessibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileInaccessibleException(Throwable cause) {
        super(cause);
    }

    public FileInaccessibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
