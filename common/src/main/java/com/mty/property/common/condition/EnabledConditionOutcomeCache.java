package com.mty.property.common.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;

import java.util.function.Function;

public interface EnabledConditionOutcomeCache {
    ConditionOutcome computeIfAbsent(Class clazz, Function<Class, ConditionOutcome> mappingFunction);

    void clear();

}
