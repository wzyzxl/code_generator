package cn.com.web.wzy.controller;

import cn.com.web.wzy.entity.ResponseBodyEntity;
import cn.com.web.wzy.service.ex.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 基础controller类，所有controller层对象必须继承这个类
 */
public class BaseController {

    // 用于统一处理抛出的异常
    @ExceptionHandler(ServiceException.class)
    public ResponseBodyEntity<Void> handleException(Throwable e) {
        if (e instanceof DatabaseTypeNotFoundException || e instanceof ConnectionInformationErrorException) {
            return ResponseBodyEntity.error(400, e.getMessage());
        } else if (e instanceof ConfigurationNameDuplicateException) {
            return ResponseBodyEntity.error(409, e.getMessage());
        } else if (e instanceof FileNotFoundException) {
            return ResponseBodyEntity.error(404, e.getMessage());
        } else if (e instanceof FileGenerationErrorException) {
            return ResponseBodyEntity.error(500, e.getMessage());
        } else {
            return ResponseBodyEntity.error(e.getMessage());
        }
    }
}
