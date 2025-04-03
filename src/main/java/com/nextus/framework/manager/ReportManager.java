package com.nextus.framework.manager;

import com.microsoft.playwright.Page;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.nextus.framework.constants.FrameworkConstants;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manages test reporting functionality with Allure integration.
 */
@Slf4j
public final class ReportManager {
    
    private ReportManager() {
        // Utility class
    }

    /**
     * Adds a test step with details.
     */
    public static void addStep(String stepName, String details) {
        try {
            Allure.step(stepName, () -> {
                if (details != null && !details.isEmpty()) {
                    Allure.addAttachment("Details", details);
                }
            });
        } catch (Exception e) {
            log.error("Failed to add step: {}", stepName, e);
        }
    }

    /**
     * Captures and attaches a screenshot.
     */
    public static void attachScreenshot(Page page, String name) {
        try {
            byte[] screenshot = page.screenshot();
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            log.error("Failed to attach screenshot: {}", name, e);
        }
    }

    /**
     * Attaches a video recording.
     */
    public static void attachVideo(Path videoPath) {
        try {
            if (videoPath != null && Files.exists(videoPath)) {
                byte[] videoBytes = Files.readAllBytes(videoPath);
                Allure.addAttachment("Video Recording", "video/webm", new ByteArrayInputStream(videoBytes), "webm");
            }
        } catch (Exception e) {
            log.error("Failed to attach video: {}", videoPath, e);
        }
    }

    /**
     * Handles test failure by capturing screenshots and logs.
     */
    public static void handleTestFailure(Page page, String testName, Throwable error) {
        try {
            attachScreenshot(page, "Failure Screenshot");
            Allure.addAttachment("Error Details", error.getMessage());
            log.error("Test failed: {}", testName, error);
        } catch (Exception e) {
            log.error("Failed to handle test failure for: {}", testName, e);
        }
    }

    /**
     * Sets test metadata (severity, category, etc.)
     */
    public static void setTestMetadata(String category, SeverityLevel severity) {
        Allure.label("category", category);
        Allure.label("severity", severity.toString());
    }
}