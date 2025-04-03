// FrameworkConfigFactory.java
package com.nextus.framework.factory;

import com.nextus.framework.config.FrameworkConfig;
import org.aeonbits.owner.ConfigFactory;

public class FrameworkConfigFactory {

    private static final FrameworkConfig config;

    static {
        System.setProperty("env", System.getProperty("env", "qa"));
        config = ConfigFactory.create(FrameworkConfig.class);
    }

    private FrameworkConfigFactory() {
        // Prevent instantiation
    }

    public static FrameworkConfig getConfig() {
        return config;
    }
}