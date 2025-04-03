package com.nextus.framework.assertions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class ElementAssert {
    private final Page page;
    private Locator locator;
    private String selector;
    private final NextusAssert root; // Root referansı ekle

    ElementAssert(Page page, NextusAssert root) { // Constructor'ı güncelle
        this.page = page;
        this.root = root;
    }

    ElementAssert withSelector(String selector) {
        this.selector = selector;
        this.locator = page.locator(selector);
        return this;
    }

    ElementAssert withLocator(Locator locator) {
        this.locator = locator;
        return this;
    }

    // Root'a dönüş metodu
    public NextusAssert and() {
        return root;
    }

    public ElementAssert isVisible() {
        Assertions.assertTrue(locator.isVisible(), 
            String.format("Element '%s' should be visible", selector));
        return this;
    }

    public ElementAssert isHidden() {
        Assertions.assertFalse(locator.isVisible(), 
            String.format("Element '%s' should be hidden", selector));
        return this;
    }

    public ElementAssert hasText(String expectedText) {
        String actualText = locator.textContent();
        Assertions.assertEquals(expectedText, actualText,
            String.format("Element '%s' text mismatch", selector));
        return this;
    }

    public ElementAssert containsText(String expectedText) {
        String actualText = locator.textContent();
        Assertions.assertTrue(actualText.contains(expectedText),
            String.format("Element '%s' should contain text '%s'", selector, expectedText));
        return this;
    }

    public ElementAssert hasValue(String expectedValue) {
        String actualValue = locator.inputValue();
        Assertions.assertEquals(expectedValue, actualValue,
            String.format("Element '%s' value mismatch", selector));
        return this;
    }

    public ElementAssert isEnabled() {
        Assertions.assertTrue(locator.isEnabled(),
            String.format("Element '%s' should be enabled", selector));
        return this;
    }

    public ElementAssert isDisabled() {
        Assertions.assertFalse(locator.isEnabled(),
            String.format("Element '%s' should be disabled", selector));
        return this;
    }

    public ElementAssert hasAttribute(String attribute, String expectedValue) {
        String actualValue = locator.getAttribute(attribute);
        Assertions.assertEquals(expectedValue, actualValue,
            String.format("Element '%s' attribute '%s' mismatch", selector, attribute));
        return this;
    }

    public ElementAssert hasClass(String expectedClass) {
        String classes = locator.getAttribute("class");
        Assertions.assertTrue(classes != null && classes.contains(expectedClass),
            String.format("Element '%s' should have class '%s'", selector, expectedClass));
        return this;
    }

    public ElementAssert exists() {
        Assertions.assertNotNull(locator.elementHandle(),
            String.format("Element '%s' should exist in DOM", selector));
        return this;
    }

    public ElementAssert doesNotExist() {
        Assertions.assertNull(locator.elementHandle(),
            String.format("Element '%s' should not exist in DOM", selector));
        return this;
    }
}