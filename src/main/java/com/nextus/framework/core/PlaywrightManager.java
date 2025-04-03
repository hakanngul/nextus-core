package com.nextus.framework.core;

import com.microsoft.playwright.*;
import com.nextus.framework.factory.FrameworkConfigFactory;

/**
 * Thread-safe manager for Playwright and Browser instances using ThreadLocal.
 * Ensures isolated browser sessions for parallel test execution.
 */
public final class PlaywrightManager {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();

    private PlaywrightManager() {
        // prevent instantiation
    }

    public static Playwright getPlaywright() {
        if (playwright.get() == null) {
            playwright.set(Playwright.create());
        }
        return playwright.get();
    }

    public static Browser getBrowser() {
        if (browser.get() == null) {
            String browserType = FrameworkConfigFactory.getConfig().browser().toLowerCase();
            boolean isHeadless = FrameworkConfigFactory.getConfig().isHeadless();

            Browser launchedBrowser;

            switch (browserType) {
                case "chromium":
                    launchedBrowser = getPlaywright()
                            .chromium()
                            .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                case "firefox":
                    launchedBrowser = getPlaywright()
                            .firefox()
                            .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                case "webkit":
                    launchedBrowser = getPlaywright()
                            .webkit()
                            .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                default:
                    throw new RuntimeException("Unsupported browser type: " + browserType);
            }

            browser.set(launchedBrowser);
        }

        return browser.get();
    }

    public static void close() {
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }
}