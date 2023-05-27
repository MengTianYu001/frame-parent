package com.mty.property.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mty
 * @since 2022/09/15 13:49
 **/
@Slf4j
public class TraceLogUtil {
    public static void trace(boolean enable, String format, Object... messages) {
        if (log.isDebugEnabled() && enable) {
            log.info(format, messages);
        }
    }
}
