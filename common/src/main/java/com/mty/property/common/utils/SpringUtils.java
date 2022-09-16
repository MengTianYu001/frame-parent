package com.mty.property.common.utils;

import com.mty.property.common.application.ApplicationNameAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author mty
 * @date 2022/09/15 12:02
 **/
public class SpringUtils implements ApplicationContextAware, ApplicationNameAware {
    private static ApplicationContext applicationContext;

    private static String appName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;

    }
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String beanName) {
        return (T)applicationContext.getBean(beanName);
    }

    public static String getAppName() {
        return appName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        appName = applicationName;

    }
}
