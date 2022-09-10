package com.mty.property.common.advisor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mty
 * @date 2022/08/25 16:13
 **/
public class BeanAnnotationHolder<T extends Annotation> implements BeanAnnotationHoldable<T> {
    private Map<Class, Map<Method, BeanAnnotation<T>>> classMethodBeanAnnotationMap = new HashMap<>();

    @Override
    public BeanAnnotation<T> getBeanAnnotation(Class aclass, Method method) {
        Map<Method, BeanAnnotation<T>> methodBeanAnnotationMap = classMethodBeanAnnotationMap.get(aclass);
        if (methodBeanAnnotationMap == null) {
            return null;
        }
        return methodBeanAnnotationMap.get(method);
    }

    @Override
    public void hold(Class aclass, Method method, BeanAnnotation<T> annotation) {
        Map<Method, BeanAnnotation<T>> methodBeanAnnotationMap = classMethodBeanAnnotationMap.computeIfAbsent(aclass, k -> new HashMap<>());
        methodBeanAnnotationMap.put(method, annotation);
    }
}
