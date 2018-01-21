package com.switchvov.servlet3test.common.thread;

import com.switchvov.servlet3test.common.ResponseResult;
import com.switchvov.servlet3test.common.util.JacksonUtils;
import com.switchvov.servlet3test.conf.thread.CanceledCallable;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
@Component
public class CoreAsyncContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreAsyncContext.class);

    private static final Integer ASYNC_TIMEOUT_IN_SECONDS = 30;

    @Autowired
    @Qualifier("coreAsyncListener")
    private AsyncListener coreAsyncListener;

    @Autowired
    @Qualifier("coreExecutor")
    private ThreadPoolExecutor coreExecutor;

    public void submitFuture(final HttpServletRequest request, final Callable<Object> task) {
        final String uri = request.getRequestURI();
        final Map<String, String[]> params = request.getParameterMap();
        final AsyncContext asyncContext = request.startAsync();

        asyncContext.getRequest().setAttribute("uri", uri);
        asyncContext.getRequest().setAttribute("params", params);
        asyncContext.setTimeout(ASYNC_TIMEOUT_IN_SECONDS * 1000);
        asyncContext.addListener(coreAsyncListener);

        coreExecutor.submit(new CanceledCallable<Object>(asyncContext) {
            @Override
            public Object call() throws Exception {
                Object result = task.call();
                if (Objects.isNull(result)) {
                    callback(asyncContext, null, uri, params);
                }
                if (result instanceof CompletableFuture) {
                    CompletableFuture<Object> future = (CompletableFuture<Object>) result;
                    future.thenAccept(resultObject ->
                            callback(asyncContext, resultObject, uri, params))
                            .exceptionally(e -> {
                                callback(asyncContext, "", uri, params);
                                return null;
                            });
                } else {
                    callback(asyncContext, result, uri, params);
                }
                return null;
            }
        });
    }

    private void callback(AsyncContext asyncContext, Object result, String uri, Map<String, String[]> params) {
        HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
        try {
            ResponseResult<?> responseResult = ResponseResult.successWithData(result);
            response.setContentType("application/json;charset=UTF-8");
            write(response, JacksonUtils.toJSon(responseResult));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                LOGGER.error("get info error, uri: {}, params: {}, stackTrace: {}", uri, JacksonUtils.toJSon(params), ExceptionUtils.getStackTrace(e));
            } catch (Exception ex) {

            }
        } finally {
            asyncContext.complete();
        }
    }

    private void write(HttpServletResponse response, String result) throws IOException {
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
