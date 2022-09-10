package com.mty.property.common.advisor;

import com.mty.property.common.utils.PropertySourceUtils;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

/**
 * @author mty
 * @date 2022/08/25 14:52
 **/
public abstract class AbstractCachedAdvisor<T extends Annotation> extends AbstractBeanFactoryPointcutAdvisor {
    private BeanAnnotationHolder<T> beanAnnotationHolder = new BeanAnnotationHolder<>();

    private Class<T> annotationType;

    @Autowired
    private PropertySourceUtils propertySourceUtils;
}
