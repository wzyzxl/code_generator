package cn.com.web.wzy.service.ex;

/**
 * 文件生成错误异常
 */
public class FileGenerationErrorException extends ServiceException{
    public FileGenerationErrorException() {
        super("文件生成错误异常");
    }

    public FileGenerationErrorException(String message) {
        super(message);
    }

    public FileGenerationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileGenerationErrorException(Throwable cause) {
        super(cause);
    }

    public FileGenerationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
