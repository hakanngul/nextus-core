package com.nextus.framework.strategy;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public interface DriverStrategy {
    Browser launchBrowser(Playwright playwright);
}