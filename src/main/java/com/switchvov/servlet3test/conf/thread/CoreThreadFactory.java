package com.switchvov.servlet3test.conf.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
public class CoreThreadFactory implements ThreadFactory {
    public static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    private final String namePrefix;

    public CoreThreadFactory() {
        namePrefix = "corepool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, namePrefix + THREAD_NUMBER.getAndIncrement());
    }
}
