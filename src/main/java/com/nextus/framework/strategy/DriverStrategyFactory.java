package com.nextus.framework.strategy;

/**
 * Factory that returns the appropriate driver strategy
 * based on environment configuration.
 */
public class DriverStrategyFactory {

    public static DriverStrategy getStrategy() {
        // Future: use env/system property to switch strategies
        return new LocalDriverStrategy(); // for now only local
    }
}