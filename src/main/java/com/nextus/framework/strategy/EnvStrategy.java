package com.nextus.framework.strategy;

import com.nextus.framework.factory.FrameworkConfigFactory;

/**
 * EnvStrategy allows environment-specific behaviors
 * by reading the 'env' system property or config value.
 */
public final class EnvStrategy {

    private EnvStrategy() {
        // Prevent instantiation
    }

    public static String getCurrentEnv() {
        return System.getProperty("env", "qa");
    }

    public static boolean isQA() {
        return getCurrentEnv().equalsIgnoreCase("qa");
    }

    public static boolean isStaging() {
        return getCurrentEnv().equalsIgnoreCase("staging");
    }

    public static boolean isDev() {
        return getCurrentEnv().equalsIgnoreCase("dev");
    }

    public static boolean isProd() {
        return getCurrentEnv().equalsIgnoreCase("prod");
    }
}