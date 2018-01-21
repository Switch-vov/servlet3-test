package com.switchvov.servlet3test.conf.thread;

import javax.servlet.AsyncContext;
import java.util.concurrent.Callable;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
public abstract class CanceledCallable<V> implements Callable<V> {
    private AsyncContext asyncContext;

    public CanceledCallable(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    public AsyncContext getAsyncContext() {
        return asyncContext;
    }
}
