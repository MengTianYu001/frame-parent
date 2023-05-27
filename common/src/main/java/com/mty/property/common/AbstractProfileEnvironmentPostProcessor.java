package com.mty.property.common;

import com.mty.property.common.utils.SpringApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author mty
 * @since 2022/09/16 14:28
 **/
public class AbstractProfileEnvironmentPostProcessor extends AbstractBootstrapProfileEnvironmentPostProcessor{
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {
        if (!SpringApplicationUtils.isbootApplication(application)) {
            return;
        }
        super.postProcessEnvironment(env, application);
    }
}
