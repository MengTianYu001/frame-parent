package com.mty.property.common.condition;

import com.mty.property.common.infrastructure.BootApplicationListener;
import org.springframework.boot.context.event.ApplicationStartedEvent;

/**
 * @author mty
 * @since 2022/09/19 11:20
 **/
public class EnabledConditionOutcomeCacheCleaner implements BootApplicationListener<ApplicationStartedEvent> {

    @Override
    public void doOnApplicationEvent(ApplicationStartedEvent event) {
        SimpleMapEnabledConditionOutcomeCache.getInstance().clear();
    }
}
