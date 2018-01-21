package com.switchvov.servlet3test.conf.thread;

import com.switchvov.servlet3test.common.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
public class CoreAsyncListener implements AsyncListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreAsyncListener.class);

    @Override
    public void onComplete(AsyncEvent event) throws IOException {

    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        AsyncContext asyncContext = event.getAsyncContext();
        try {
            ServletRequest request = asyncContext.getRequest();
            String uri = (String) request.getAttribute("uri");
            Map params = (Map) request.getAttribute("params");
            LOGGER.error("async request timeout, uri: {}. params: {}", uri, JacksonUtils.toJSon(params));
        } catch (Exception e) {

        }

        try {
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            asyncContext.complete();
        }
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        AsyncContext asyncContext = event.getAsyncContext();
        try {
            ServletRequest request = asyncContext.getRequest();
            String uri = (String) request.getAttribute("uri");
            Map params = (Map) request.getAttribute("params");
            LOGGER.error("async request error, uri: {}. params: {}", uri, JacksonUtils.toJSon(params));
        } catch (Exception e) {

        }

        try {
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            asyncContext.complete();
        }
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {

    }
}
