package com.nextus.framework.assertions;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class PageAssert {
    private final Page page;
    private final NextusAssert root;

    PageAssert(Page page, NextusAssert root) {
        this.page = page;
        this.root = root;
    }

    public NextusAssert and() {
        return root;
    }

    public PageAssert hasUrl(String expectedUrl) {
        Assertions.assertEquals(expectedUrl, page.url(),
            "Page URL mismatch");
        return this;
    }

    public PageAssert urlContains(String expectedUrlPart) {
        Assertions.assertTrue(page.url().contains(expectedUrlPart),
            String.format("Page URL should contain '%s'", expectedUrlPart));
        return this;
    }

    public PageAssert hasTitle(String expectedTitle) {
        Assertions.assertEquals(expectedTitle, page.title(),
            "Page title mismatch");
        return this;
    }

    public PageAssert titleContains(String expectedTitlePart) {
        Assertions.assertTrue(page.title().contains(expectedTitlePart),
            String.format("Page title should contain '%s'", expectedTitlePart));
        return this;
    }
}