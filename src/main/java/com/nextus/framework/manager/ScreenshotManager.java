package com.nextus.framework.manager;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.nextus.framework.factory.FrameworkConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manages screenshot capture functionality using Playwright.
 * Supports different types of screenshots (full page, element, viewport)
 * with configurable options.
 */
@Slf4j
public final class ScreenshotManager {

    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    private static final String FILE_EXTENSION = ".png";

    private ScreenshotManager() {
        // Utility class
    }

    /**
     * Takes a screenshot of the current viewport.
     *
     * @param page The Playwright page instance
     * @param testName Name of the test for the screenshot file
     * @return Path to the saved screenshot
     */
    public static Path takeScreenshot(Page page, String testName) {
        return takeScreenshot(page, testName, false, false);
    }

    /**
     * Takes a screenshot with configurable options.
     *
     * @param page The Playwright page instance
     * @param testName Name of the test for the screenshot file
     * @param fullPage Whether to take a full page screenshot
     * @param omitBackground Whether to hide default white background
     * @return Path to the saved screenshot
     */
    public static Path takeScreenshot(Page page, String testName, boolean fullPage, boolean omitBackground) {
        try {
            String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            String fileName = String.format("%s_%s%s", testName, timestamp, FILE_EXTENSION);
            Path filePath = Paths.get(SCREENSHOT_DIR, fileName);

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(filePath)
                    .setFullPage(fullPage)
                    .setOmitBackground(omitBackground));

            log.info("Screenshot taken: {}", filePath);
            return filePath;
        } catch (Exception e) {
            log.error("Failed to take screenshot for test: {}", testName, e);
            return null;
        }
    }

    /**
     * Takes a screenshot of a specific element.
     *
     * @param page The Playwright page instance
     * @param selector CSS selector of the element
     * @param testName Name of the test for the screenshot file
     * @return Path to the saved screenshot
     */
    public static Path takeElementScreenshot(Page page, String selector, String testName) {
        try {
            String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            String fileName = String.format("%s_element_%s%s", testName, timestamp, FILE_EXTENSION);
            Path filePath = Paths.get(SCREENSHOT_DIR, fileName);

            Locator element = page.locator(selector);
            element.screenshot(new Locator.ScreenshotOptions()
                    .setPath(filePath));

            log.info("Element screenshot taken: {}", filePath);
            return filePath;
        } catch (Exception e) {
            log.error("Failed to take element screenshot for selector: {} in test: {}", selector, testName, e);
            return null;
        }
    }

    /**
     * Takes a screenshot on test failure if enabled in configuration.
     *
     * @param page The Playwright page instance
     * @param testName Name of the test for the screenshot file
     * @return Path to the saved screenshot
     */
    public static Path takeFailureScreenshot(Page page, String testName) {
        if (!FrameworkConfigFactory.getConfig().takeScreenshotOnFailure()) {
            log.info("Failure screenshot is disabled in configuration");
            return null;
        }

        try {
            String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            String fileName = String.format("%s_FAILURE_%s%s", testName, timestamp, FILE_EXTENSION);
            Path filePath = Paths.get(SCREENSHOT_DIR, fileName);

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(filePath)
                    .setFullPage(true));

            log.info("Failure screenshot taken: {}", filePath);
            return filePath;
        } catch (Exception e) {
            log.error("Failed to take failure screenshot for test: {}", testName, e);
            return null;
        }
    }

    /**
     * Takes a screenshot with custom options.
     *
     * @param page The Playwright page instance
     * @param testName Name of the test for the screenshot file
     * @param options Custom screenshot options
     * @return Path to the saved screenshot
     */
    public static Path takeScreenshotWithOptions(Page page, String testName, Page.ScreenshotOptions options) {
        try {
            String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            String fileName = String.format("%s_%s%s", testName, timestamp, FILE_EXTENSION);
            Path filePath = Paths.get(SCREENSHOT_DIR, fileName);

            options.setPath(filePath);
            page.screenshot(options);

            log.info("Screenshot taken with custom options: {}", filePath);
            return filePath;
        } catch (Exception e) {
            log.error("Failed to take screenshot with custom options for test: {}", testName, e);
            return null;
        }
    }

    /**
     * Checks if a screenshot file exists at the specified path.
     *
     * @param screenshotPath Path to check
     * @return true if screenshot exists, false otherwise
     */
    public static boolean screenshotExists(Path screenshotPath) {
        return screenshotPath != null && screenshotPath.toFile().exists();
    }
}