package cn.com.web.wzy.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constant {
    // 日志
    public static final Logger LOG = LoggerFactory.getLogger(Constant.class);

    // 数据库资源连接超时(2小时)
    public static final long CONNECTION_EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    // 删除过期数据库连接资源时线程内间隔时间(30分钟)
    public static final long DELETE_EXPIRED_DATABASE_RESOURCES =  30 * 60 * 1000;

    // 删除超时的文件
    public static final long DELETE_FILE =  5 * 60 * 1000;
}
