package com.mty.property.common.application;

import com.mty.property.common.utils.SpringApplicationUtils;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;

/**
 * @author mty
 * @since 2022/09/19 11:23
 **/
public class BootApplicationRunListenerAdapter extends ApplicationRunListenerAdapter {
    private boolean bootApp;
    public BootApplicationRunListenerAdapter(SpringApplication application, String[] args) {
        super(application, args);
    }

    private void init() {
        bootApp = SpringApplicationUtils.isbootApplication(getApplication());
    }
    @Override
    public final void starting(ConfigurableBootstrapContext bootstrapContext){
        init();
        if (bootApp) {
            doStarting();
        }
    }
    protected void doStarting() {}

}
