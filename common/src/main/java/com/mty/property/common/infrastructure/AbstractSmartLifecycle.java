package com.mty.property.common.infrastructure;

import org.springframework.context.SmartLifecycle;

/**
 * @author mty
 * @date 2022/09/19 10:25
 **/
public abstract class AbstractSmartLifecycle implements SmartLifecycle {
    private volatile boolean running = false;

    @Override
    public void start() {
        doStart();
        running = true;
    }

    public abstract void doStart();

    @Override
    public void stop() {
        doStop();
        running = false;
    }

    public abstract void doStop();

    @Override
    public boolean isRunning() {
        return running;
    }

}
