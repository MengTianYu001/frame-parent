package com.mty.property.common.advisor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface BeanAnnotationHoldable<T extends Annotation> {
    BeanAnnotation<T> getBeanAnnotation(Class aclass, Method method);

    void hold(Class aclass, Method method, BeanAnnotation<T> annotation);

}
