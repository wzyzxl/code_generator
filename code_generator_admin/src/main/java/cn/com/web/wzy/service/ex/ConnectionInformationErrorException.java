package cn.com.web.wzy.service.ex;

/**
 * 连接信息错误异常
 */
public class ConnectionInformationErrorException extends ServiceException{
    public ConnectionInformationErrorException() {
        super("连接信息错误异常");
    }

    public ConnectionInformationErrorException(String message) {
        super(message);
    }

    public ConnectionInformationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionInformationErrorException(Throwable cause) {
        super(cause);
    }

    public ConnectionInformationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
