package com.mty.property.common;

import com.mty.property.common.utils.SpringApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author mty
 * @date 2022/09/16 14:28
 **/
public class AbstractProfileEnvironmentPostProcessor extends AbstractBootstrapProfileEnvironmentPostProcessor{
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {
        if (!SpringApplicationUtil.isbootApplication(application)) {
            return;
        }
        super.postProcessEnvironment(env, application);
    }
}
