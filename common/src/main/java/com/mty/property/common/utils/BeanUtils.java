package com.mty.property.common.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author mty
 * @since 2022/08/26 14:58
 **/
@UtilityClass
public class BeanUtils {
    @SneakyThrows
    public static <T> void mergeBean(T srcObj, T dstObj) {
        Class type = srcObj.getClass();
        PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(type);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName)) {
                continue;
            }
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (readMethod != null && writeMethod != null) {
                Object value = readMethod.invoke(srcObj);
                if (value != null) {
                    writeMethod.invoke(dstObj, value);
                }
            }
        }
    }
}
