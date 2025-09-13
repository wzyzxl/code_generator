package cn.com.web.wzy.config;

import cn.com.web.wzy.utils.ThreadPoolUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    /**
     * 配置线程池并设置到工具类中
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 核心线程数
        int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
        // 最大线程数
        int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        // 空闲线程存活时间
        long keepAliveTime = 60;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 工作队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1024);
        // 线程工厂，设置线程名前缀
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 1;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("custom-thread-pool-" + count);
                count++;
                return thread;
            }
        };
        // 拒绝策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );

        // 将线程池设置到工具类中
        ThreadPoolUtils.setExecutor(executor);

        return executor;
    }
}
