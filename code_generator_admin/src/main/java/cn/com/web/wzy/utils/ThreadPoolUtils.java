package cn.com.web.wzy.utils;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池工具类，提供静态方法操作线程池
 */
public class ThreadPoolUtils {

    // 线程池实例
    private static ThreadPoolExecutor executor;

    /**
     * 设置线程池实例，由Spring配置类调用
     */
    public static void setExecutor(ThreadPoolExecutor executor) {
        ThreadPoolUtils.executor = executor;
    }

    /**
     * 执行无返回值的任务
     */
    public static void execute(Runnable task) {
        checkExecutor();
        executor.execute(task);
    }

    /**
     * 提交有返回值的任务
     */
    public static <T> Future<T> submit(Callable<T> task) {
        checkExecutor();
        return executor.submit(task);
    }

    /**
     * 提交Runnable任务并指定返回值
     */
    public static <T> Future<T> submit(Runnable task, T result) {
        checkExecutor();
        return executor.submit(task, result);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    /**
     * 立即关闭线程池
     */
    public static List<Runnable> shutdownNow() {
        if (executor != null) {
            return executor.shutdownNow();
        }
        return null;
    }

    /**
     * 检查线程池是否已初始化
     */
    private static void checkExecutor() {
        if (executor == null) {
            throw new IllegalStateException("线程池尚未初始化，请检查配置");
        }
    }
}
