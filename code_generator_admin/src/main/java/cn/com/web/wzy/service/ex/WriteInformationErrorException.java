package cn.com.web.wzy.service.ex;

/**
 * 写入信息错误异常
 */
public class WriteInformationErrorException extends ServiceException{
    public WriteInformationErrorException() {
        super("写入信息错误异常。");
    }

    public WriteInformationErrorException(String message) {
        super(message);
    }

    public WriteInformationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteInformationErrorException(Throwable cause) {
        super(cause);
    }

    public WriteInformationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
