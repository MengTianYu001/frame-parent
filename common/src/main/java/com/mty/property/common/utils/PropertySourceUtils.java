package com.mty.property.common.utils;

import cn.hutool.core.lang.Assert;
import lombok.experimental.UtilityClass;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author mty
 * @since 2022/08/25 16:58
 **/
@UtilityClass
public class PropertySourceUtils {
    private static final String AOPS_PROPERTIES = "aops_properties";

    private static final String PRIORITY_PROPERTIES = "priority_properties";

    private static final String INNER_PROPERTIES = "inner_properties";

    public static void put(ConfigurableEnvironment environment, String name, Object object) {
        Map<String, Object> lacation = prepareOrGetDefaultLocation(environment);
        lacation.put(name, object);
    }

    public static String getAppName(Environment environment) {
        String appName = environment.getProperty("spring.application.name");
        Assert.notNull(appName, "spring.application name must not be null");
        return appName;
    }

    public static Object get(ConfigurableEnvironment environment, String name) {
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        return location.get(name);
    }

    public static void putPriority(ConfigurableEnvironment environment, String name, Object object) {
        Map<String, Object> priority = prepareOrGetPriorityLocation(environment);
        priority.put(name, object);
    }



    private static final String SCAN_PACKAGES = "aops_scan_packages";

    public static List<String> getBasePackages(ConfigurableEnvironment environment) {
        Map<String, Object> location = prepareOrGetInnerLocations(environment);
        return (List<String>) location.get(SCAN_PACKAGES);
    }

    public static void setBasePackages(ConfigurableEnvironment environment, List<String> packages) {
        Map<String, Object> location = prepareOrGetInnerLocations(environment);
        location.put(SCAN_PACKAGES, packages);
    }

    protected static final String EXCLUDE_NAMES = "spring.autoconfigure.exclude";
    public static void excludeAutoconfigure(ConfigurableEnvironment environment, String name) {
        String old = environment.getProperty(EXCLUDE_NAMES);
        if (StringUtils.hasText(old)) {
            putPriority(environment, EXCLUDE_NAMES, old + "," + name);
        }else {
            putPriority(environment, EXCLUDE_NAMES, name);
        }
    }

    protected static Map<String, Object> prepareOrGetDefaultLocation(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, AOPS_PROPERTIES, MutablePropertySources::addLast);
    }

    public static Map<String, Object> prepareOrGetPriorityLocation(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, PRIORITY_PROPERTIES, MutablePropertySources::addFirst);
    }

    private static Map<String, Object> prepareOrGetInnerLocations(ConfigurableEnvironment environment) {
        return prepareOrGetMapSource(environment, INNER_PROPERTIES, MutablePropertySources::addLast);
    }

    public static Map<String, Object> prepareOrGetMapSource(ConfigurableEnvironment environment, String sourceName, BiConsumer<MutablePropertySources, MapPropertySource> sourceLocFunction) {
        MutablePropertySources propertySources = environment.getPropertySources();
        MapPropertySource mapPropertySource = (MapPropertySource) propertySources.get(sourceName);
        Map<String, Object> source;
        if (mapPropertySource == null) {
            source = new HashMap<>();
            mapPropertySource = new MapPropertySource(sourceName, source);
            sourceLocFunction.accept(propertySources, mapPropertySource);
        } else {
            source = mapPropertySource.getSource();
        }
        return source;
    }






}
