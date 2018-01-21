package com.switchvov.servlet3test.conf.thread;

import com.switchvov.servlet3test.common.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
public class CoreRejectedExecutionHandler implements RejectedExecutionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof CanceledCallable) {
            CanceledCallable cc = (CanceledCallable) r;
            AsyncContext asyncContext = cc.getAsyncContext();
            if (Objects.nonNull(asyncContext)) {
                try {
                    ServletRequest request = asyncContext.getRequest();
                    String uri = (String) request.getAttribute("uri");
                    Map params = (Map) request.getAttribute("params");
                    LOGGER.error("async request rejected, uri: {}. params: {}", uri, JacksonUtils.toJSon(params));
                } catch (Exception e) {

                }

                try {
                    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } finally {
                    asyncContext.complete();
                }
            }
        }
    }
}
