package cn.com.web.wzy.service.ex;

/**
 * 文件不存在异常
 */
public class FileNotFoundException extends ServiceException{
    public FileNotFoundException() {
        super("文件不存在异常");
    }

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
