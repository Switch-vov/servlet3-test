package com.switchvov.servlet3test.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
public class JacksonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtils.class);

    private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER = ThreadLocal.withInitial(ObjectMapper::new);

    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.get().readValue(jsonStr, valueType);
        } catch (Exception e) {
            LOGGER.error("readValue error, cause: {}, stackTrace: {}", e.getMessage(), ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static String toJSon(Object object) {
        try {
            return OBJECT_MAPPER.get().writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("toJSon error, cause: {}, stackTrace: {}", e.getMessage(), ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
