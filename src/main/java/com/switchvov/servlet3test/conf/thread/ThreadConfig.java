package com.switchvov.servlet3test.conf.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.AsyncListener;
import java.util.concurrent.*;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
@Configuration
public class ThreadConfig {
    private static final Integer CORE_POOL_SIZE = 30;
    private static final Integer MAX_POOL_SIZE = 100;
    private static final Integer QUEUE_CAPACITY = 10000;
    private static final Integer KEEP_ALIVE_TIME_IN_SECONDS = 50;

    @Bean
    public ThreadPoolExecutor coreExecutor() {
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(QUEUE_CAPACITY);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME_IN_SECONDS, TimeUnit.SECONDS, queue, new CoreThreadFactory());
        executor.allowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new CoreRejectedExecutionHandler());
        return executor;
    }

    @Bean
    public AsyncListener coreAsyncListener() {
        return new CoreAsyncListener();
    }
}
