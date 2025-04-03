package com.nextus.framework.manager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Video;
import com.nextus.framework.factory.FrameworkConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages video recording for Playwright tests.
 * Videos are saved upon browser context closure at the end of a test.
 */
@Slf4j
public final class VideoManager {

    private static final String VIDEO_DIR = "videos";
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private VideoManager() {
        // Utility class
    }

    /**
     * Creates new browser context with video recording enabled.
     *
     * @param browser The browser instance
     * @return BrowserContext configured for video recording
     */
    public static BrowserContext createContextWithVideo(Browser browser) {
        if (!FrameworkConfigFactory.getConfig().recordVideo()) {
            return browser.newContext();
        }

        return browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(VIDEO_DIR))
                .setRecordVideoSize(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    /**
     * Creates new browser context with custom video size.
     *
     * @param browser The browser instance
     * @param width Video width
     * @param height Video height
     * @return BrowserContext configured for video recording
     */
    public static BrowserContext createContextWithVideo(Browser browser, int width, int height) {
        if (!FrameworkConfigFactory.getConfig().recordVideo()) {
            return browser.newContext();
        }

        return browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(VIDEO_DIR))
                .setRecordVideoSize(width, height));
    }

    /**
     * Gets the video file path for a specific page.
     * Should be called after the test is complete and context is closed.
     *
     * @param page The page instance
     * @return Path to the recorded video file
     */
    public static Path getVideoPath(Page page) {
        try {
            Video video = page.video();
            return video != null ? video.path() : null;
        } catch (Exception e) {
            log.error("Failed to get video path", e);
            return null;
        }
    }

    /**
     * Saves the video by closing the context.
     * Videos are only saved when the context is closed.
     *
     * @param context The browser context to close
     */
    public static void saveVideo(BrowserContext context) {
        if (context != null) {
            try {
                context.close();
                log.info("Browser context closed, video saved");
            } catch (Exception e) {
                log.error("Failed to close context and save video", e);
            }
        }
    }
}