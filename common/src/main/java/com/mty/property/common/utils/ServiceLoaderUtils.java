package com.mty.property.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * @author mty
 * @date 2022/08/29 11:13
 **/
@UtilityClass
public class ServiceLoaderUtils {
    public static <T> T loadFirst(Class<T> tClass) {
        Iterator<T> iterator = loadAll(tClass);
        return iterator.next();
    }

    public static <S> Iterator<S> loadAll(Class<S> clazz) {
        Iterator<S> iterator = loadAllIfPresent(clazz);
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format("No implementation defined in /META-INF/SERVICES/%S, please check whether the file exists and has the right implementation class!", clazz.getName()));
        }
        return iterator;
    }

    public static <S> Iterator<S> loadAllIfPresent(Class<S> clazz) {
        ServiceLoader<S> loader = ServiceLoader.load(clazz);
        return loader.iterator();
    }

    public static <T> T loadByOrder(Class<T> tClass) {
        Iterator<T> iterator = loadAll(tClass);
        LinkedList<T> instances = new LinkedList<>();
        while (iterator.hasNext()) {
            instances.add(iterator.next());
        }
        AnnotationAwareOrderComparator.sort(instances);
        return instances.getFirst();
    }
}
