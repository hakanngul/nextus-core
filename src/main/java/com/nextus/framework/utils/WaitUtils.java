package com.nextus.framework.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.Duration;

/**
 * WaitUtils provides helper methods to wait for specific element states in Playwright.
 */
public final class WaitUtils {

    private WaitUtils() {
        // Prevent instantiation
    }

    public static void waitForVisible(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    public static void waitForHidden(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN));
    }

    public static void waitForAttached(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED));
    }

    public static void waitForDetached(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.DETACHED));
    }

    public static void waitUntilEnabled(Page page, String selector) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        while (!locator.isEnabled()) {
            try {
                Thread.sleep(getTimeout(100)); // kısa bekleme
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void waitUntilDisabled(Page page, String selector) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        while (locator.isEnabled()) {
            try {
                Thread.sleep(getTimeout(100)); // etkinliği kaybedene kadar bekle
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static Duration getTimeout(Number timeout) {
        if (timeout == null) {
            return Duration.ofMillis(0);
        }

        if (timeout instanceof Integer) {
            int value = timeout.intValue();
            if (value <= 60) {
                System.out.println("Seconds" + value);
                return Duration.ofSeconds(value);
            } else {
                System.out.println("Milliseconds" + value);
                return Duration.ofMillis(value);
            }
        } else {
            System.out.println("Milliseconds" + timeout.longValue());
            return Duration.ofMillis(timeout.longValue());
        }
    }

}