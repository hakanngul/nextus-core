package com.nextus.framework.manager;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotManager {

    private ScreenshotManager() {
        // Utility class
    }

    public static void takeScreenshot(Page page, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "target/screenshots/" + testName + "_" + timestamp + ".png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
        System.out.println("Screenshot taken: " + filePath);
    }
}