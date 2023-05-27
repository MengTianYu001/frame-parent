package com.mty.property.common.utils;

import org.slf4j.MDC;

/**
 * @author mty
 * @since 2022/09/15 13:52
 **/
public class TraceUtils {
    public static String getReqId() {
        return MDC.get("traceId");


    }
}
