package com.mty.property.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author mty
 * @since 2022/09/16 11:34
 **/
public class AbstractBootstrapProfileEnvironmentPostProcessor implements EnvironmentPostProcessor {
    public static final String LOCAL = "local";
    public static final String STG = "stg";
    public static final String AA_STG = "aa-stg";
    public static final String HA_STG = "ha_stg";
    public static final String PRD = "prd";
    public static final String DEV = "dev";
    public static final String IT = "it";
    public static final String AA_PRD = "aa-prd";
    public static final String HA_PRD = "ha-prd";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {
        if (!shouldProcess(env, application)) {
            return;
        }
        String[] activeProfiles = env.getActiveProfiles();
        List<String> profileList = Arrays.asList(activeProfiles);
        if (profileList.contains(AA_PRD)) {
            onPrd(env, application);
            onAaPrd(env, application);
        } else if (profileList.contains(HA_PRD)) {
            onPrd(env, application);
            onHaPrd(env, application);

        } else if (profileList.contains(PRD)) {
            onPrd(env, application);
        } else if (profileList.contains(AA_STG)) {
            onStg(env, application);
            onAaStg(env, application);
        } else if (profileList.contains(HA_STG)) {
            onStg(env, application);
            onHaStg(env, application);
        } else if (profileList.contains(STG)) {
            onStg(env, application);
        } else if (profileList.contains(DEV)) {
            onDev(env, application);
        } else if (profileList.contains(LOCAL)) {
            onLocal(env, application);
        }
        if (profileList.contains(IT)) {
            onIntegrationTest(env, application);
        }

    }

    protected void onIntegrationTest(ConfigurableEnvironment env, SpringApplication application) {

    }

    protected void onAllProfiles(ConfigurableEnvironment env, SpringApplication application) {

    }

    protected void onLocal(ConfigurableEnvironment env, SpringApplication application) {
        onDev(env, application);
    }

    protected void onDev(ConfigurableEnvironment env, SpringApplication application) {
    }

    protected void onStg(ConfigurableEnvironment env, SpringApplication application) {

    }

    protected void onHaStg(ConfigurableEnvironment env, SpringApplication application) {
    }

    protected void onAaStg(ConfigurableEnvironment env, SpringApplication application) {
    }

    protected void onPrd(ConfigurableEnvironment env, SpringApplication application) {
    }

    protected void onHaPrd(ConfigurableEnvironment env, SpringApplication application) {
    }

    protected void onAaPrd(ConfigurableEnvironment env, SpringApplication application) {

    }

    protected boolean shouldProcess(ConfigurableEnvironment env, SpringApplication application) {
        return true;
    }
}
