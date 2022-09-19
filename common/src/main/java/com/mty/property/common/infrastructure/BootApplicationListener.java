package com.mty.property.common.infrastructure;

import com.mty.property.common.utils.SpringApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;

public interface BootApplicationListener<E extends SpringApplicationEvent> extends ApplicationListener<E> {
    @Override
    default void onApplicationEvent(E event) {
        SpringApplication springApplication = event.getSpringApplication();
        if (!SpringApplicationUtil.isbootApplication(springApplication)) {
            return;
        }
        doOnApplicationEvent(event);
    }

    void doOnApplicationEvent(E event);
}
