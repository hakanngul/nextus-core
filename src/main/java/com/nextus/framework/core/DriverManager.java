package com.nextus.framework.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.nextus.framework.factory.FrameworkConfigFactory;

import java.nio.file.Paths;

/**
 * DriverManager provides thread-safe management of Page and BrowserContext instances.
 * Each thread receives its own isolated browser context and page.
 */
public final class DriverManager {

    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    private DriverManager() {
        // prevent instantiation
    }

    public static Page getPage() {
        if (page.get() == null) {
            Browser browser = PlaywrightManager.getBrowser();
            boolean recordVideo = FrameworkConfigFactory.getConfig().recordVideo();

            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

            if (recordVideo) {
                contextOptions.setRecordVideoDir(Paths.get("target/videos/"));
            }

            context.set(browser.newContext(contextOptions));
            page.set(context.get().newPage());
        }

        return page.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    public static void close() {
        if (page.get() != null) {
            page.get().close();
            page.remove();
        }

        if (context.get() != null) {
            context.get().close();
            context.remove();
        }

        PlaywrightManager.close();
    }
}