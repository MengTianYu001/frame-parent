package com.mty.property.common.utils;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author mty
 * @date 2022/08/26 14:32
 **/
@UtilityClass
public class AnnotationChangeUtils {

    public static <A extends Annotation> void addAnnotation(Class<?> des, Class<A> aClass, A anno) {
        processClassAnnotation(des, classAnnotationMap -> classAnnotationMap.put(aClass, anno));
    }

    public static <A extends Annotation> void removeAnnotation(Class<?> des, Class<A> aClass) {
        processClassAnnotation(des, classAnnotationMap -> classAnnotationMap.remove(aClass));
    }

    public static <A extends Annotation> void addAnnotation(Method method, Class<A> aClass, A anno) {
        processMethodAnnotation(method, classAnnotationMap -> classAnnotationMap.put(aClass, anno));
    }

    public static <A extends Annotation> void removeAnnotation(Method method, Class<A> aClass) {
        processMethodAnnotation(method, classAnnotationMap -> classAnnotationMap.remove(aClass));
    }


    public static void processClassAnnotation(Class<?> des, Consumer<Map<Class<? extends Annotation>, Annotation>> consumer) {
        des.getDeclaredAnnotations();
        try {
            Class<?>[] declaredClasses = Class.class.getDeclaredClasses();
            Class<?> annotationDataclass = null;
            for (Class<?> declaredClass : declaredClasses) {
                if (declaredClass.getName().equals("java.lang.Class$AnnotationData")) {
                    annotationDataclass = declaredClass;
                    break;
                }
            }

            Field annotationDataFiled = Class.class.getDeclaredField("annotationData");
            annotationDataFiled.setAccessible(true);
            Field annotationsFiled = annotationDataclass.getDeclaredField("annotations");
            annotationsFiled.setAccessible(true);
            Field declaredAnnotationDataFiled = annotationDataclass.getDeclaredField("declaredAnnotationData");
            declaredAnnotationDataFiled.setAccessible(true);
            Object annotationData = annotationsFiled.get(des);
            Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) annotationsFiled.get(annotationData);
            Map<Class<? extends Annotation>, Annotation> declaredAnnotations = (Map<Class<? extends Annotation>, Annotation>) declaredAnnotationDataFiled.get(annotationData);
            if (annotations.equals(Collections.emptyMap())) {
                annotations = new LinkedHashMap<>();
                annotationsFiled.set(annotationData, annotations);
            }
            if (declaredAnnotations.equals(Collections.emptyMap())) {
                declaredAnnotations = new LinkedHashMap<>();
                declaredAnnotationDataFiled.set(annotationData, declaredAnnotations);
            }
            consumer.accept(annotations);
            consumer.accept(declaredAnnotations);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void processMethodAnnotation(Method method, Consumer<Map<Class<? extends Annotation>, Annotation>> consumer) {
        method.getDeclaredAnnotations();
        try {
            Field annotationsFiled = Executable.class.getDeclaredField("declaredAnnotations");
            annotationsFiled.setAccessible(true);
            Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) annotationsFiled.get(method);
            if (annotations.equals(Collections.emptyMap())) {
                annotations = new LinkedHashMap<>();
                annotationsFiled.set(method, annotations);
            }
            consumer.accept(annotations);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

