package com.mty.property.common.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author mty
 * @date 2022/08/29 10:58
 **/
@UtilityClass
public class ExceptionUtils {
    public static Throwable getRealException(Throwable throwable) {
        if (throwable instanceof UndeclaredThrowableException) {
            return getRealException((UndeclaredThrowableException) throwable);
        } else if (throwable instanceof InvocationTargetException) {
            return getRealException((InvocationTargetException) throwable);
        }
        return throwable;
    }

    public static Throwable getRealException(UndeclaredThrowableException ex) {
        return getRealException(ex.getUndeclaredThrowable());
    }

    public static Throwable getRealException(InvocationTargetException ex) {
        return getRealException(ex.getTargetException());
    }
}
