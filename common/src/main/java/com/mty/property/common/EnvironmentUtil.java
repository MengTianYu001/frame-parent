package com.mty.property.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * @author mty
 * @since 2022/09/16 15:14
 **/
public final class EnvironmentUtil {
    private static Environment env;

    protected static void set(Environment env) {
        if (EnvironmentUtil.env == null) {
            EnvironmentUtil.env = env;
        }
    }

    public static String getString(String key) {
        return env.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(Objects.requireNonNull(env.getProperty(key)));

    }

    public static int getInt(String key, int defaultValue) {
        String value = env.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(Objects.requireNonNull(env.getProperty(key)));
    }

    public static Boolean getBoolean(String key) {
        return Boolean.parseBoolean(Objects.requireNonNull(env.getProperty(key)));
    }
}
