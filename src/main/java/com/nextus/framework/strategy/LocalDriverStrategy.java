package com.nextus.framework.strategy;

import com.microsoft.playwright.*;
import com.nextus.framework.config.FrameworkConfig;
import com.nextus.framework.factory.FrameworkConfigFactory;

/**
 * Launches the browser locally using Playwright.
 */
public class LocalDriverStrategy implements DriverStrategy {

    @Override
    public Browser launchBrowser(Playwright playwright) {
        FrameworkConfig config = FrameworkConfigFactory.getConfig();
        String browserType = config.browser().toLowerCase();
        boolean isHeadless = config.isHeadless();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(isHeadless);

        return switch (browserType) {
            case "chromium" -> playwright.chromium().launch(options);
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        };
    }
}