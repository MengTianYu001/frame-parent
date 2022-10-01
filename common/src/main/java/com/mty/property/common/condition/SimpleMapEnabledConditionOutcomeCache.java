package com.mty.property.common.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author mty
 * @date 2022/09/19 10:56
 **/
public class SimpleMapEnabledConditionOutcomeCache implements EnabledConditionOutcomeCache {
    private static SimpleMapEnabledConditionOutcomeCache instance = new SimpleMapEnabledConditionOutcomeCache();

    public static SimpleMapEnabledConditionOutcomeCache getInstance() {
        return instance;
    }

    private final ConcurrentHashMap<Class, ConditionOutcome> cache = new ConcurrentHashMap<>();

    @Override
    public ConditionOutcome computeIfAbsent(Class clazz, Function<Class, ConditionOutcome> mappingFunction) {
        return cache.computeIfAbsent(clazz, mappingFunction);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
