package com.nextus.framework.base;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * BasePage is the superclass for all Page Object Model (POM) classes.
 * It provides access to the Playwright Page instance and shared functionality.
 */
public abstract class BasePage {
    protected final Page page;
    protected static final int DEFAULT_TIMEOUT = 30000;

    public BasePage(Page page) {
        this.page = page;
    }

    // Navigation Methods
    public void navigate(String url) {
        page.navigate(url);
    }

    public void reload() {
        page.reload();
    }

    public void goBack() {
        page.goBack();
    }

    public void goForward() {
        page.goForward();
    }

    // Wait Methods
    public void waitForLoad() {
        page.waitForLoadState(LoadState.LOAD);
    }

    public void waitForDomContentLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void waitForUrl(String url) {
        page.waitForURL(url);
    }

    public void waitForUrlPattern(Pattern pattern) {
        page.waitForURL(pattern);
    }

    public void waitForUrlPredicate(Predicate<String> predicate) {
        page.waitForURL(predicate);
    }

    public void waitForSelector(String selector) {
        page.waitForSelector(selector);
    }

    public void waitForVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void waitForHidden(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
    }

    // Element Interaction Methods
    public void click(String selector) {
        page.click(selector);
    }

    public void clickForce(String selector) {
        page.click(selector, new Page.ClickOptions().setForce(true));
    }

    public void doubleClick(String selector) {
        page.dblclick(selector);
    }

    public void rightClick(String selector) {
        page.locator(selector).click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

    public void hover(String selector) {
        page.hover(selector);
    }

    public void focus(String selector) {
        page.focus(selector);
    }

    public void type(String selector, String text) {
        page.fill(selector, text);
    }

    public void typeSlowly(String selector, String text, int delay) {
        page.type(selector, text, new Page.TypeOptions().setDelay(delay));
    }

    public void clear(String selector) {
        page.fill(selector, "");
    }

    public void press(String selector, String key) {
        page.press(selector, key);
    }

    // Form Methods
    public void selectOption(String selector, String value) {
        page.selectOption(selector, value);
    }

    public void selectOptions(String selector, String[] values) {
        page.selectOption(selector, values);
    }

    public void checkBox(String selector) {
        page.check(selector);
    }

    public void uncheckBox(String selector) {
        page.uncheck(selector);
    }

    public void setChecked(String selector, boolean checked) {
        page.setChecked(selector, checked);
    }

    // File Operations
    public void uploadFile(String selector, Path filePath) {
        page.setInputFiles(selector, filePath);
    }

    public void uploadFiles(String selector, Path[] filePaths) {
        page.setInputFiles(selector, filePaths);
    }

    public void downloadFile(String selector) {
        page.click(selector);
        // Note: Download will be handled by Page.setDownloadHandler
    }

    // Element State Methods
    public boolean isVisible(String selector) {
        return page.isVisible(selector);
    }

    public boolean isHidden(String selector) {
        return page.isHidden(selector);
    }

    public boolean isEnabled(String selector) {
        return page.isEnabled(selector);
    }

    public boolean isDisabled(String selector) {
        return page.isDisabled(selector);
    }

    public boolean isEditable(String selector) {
        return page.isEditable(selector);
    }

    public boolean isChecked(String selector) {
        return page.isChecked(selector);
    }

    // Element Getters
    public String getText(String selector) {
        return page.textContent(selector);
    }

    public String getInputValue(String selector) {
        return page.inputValue(selector);
    }

    public String getAttribute(String selector, String attributeName) {
        return page.getAttribute(selector, attributeName);
    }

    // Multiple Elements
    public List<String> getTextContent(String selector) {
        return page.locator(selector).allTextContents();
    }

    public List<String> getInnerTexts(String selector) {
        return page.locator(selector).allInnerTexts();
    }

    public int getCount(String selector) {
        return page.locator(selector).count();
    }

    // Role-based Locators
    public void clickButton(String name) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(name)).click();
    }

    public void clickLink(String name) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(name)).click();
    }

    public void fillTextbox(String name, String value) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(name)).fill(value);
    }

    // Text-based Locators
    public void clickText(String text) {
        page.getByText(text).click();
    }

    public void clickTextExact(String text) {
        page.getByText(text, new Page.GetByTextOptions().setExact(true)).click();
    }

    // Label-based Locators
    public void getByLabel(String text) {
        page.getByLabel(text).click();
    }

    public void getByLabelExact(String text) {
        page.getByLabel(text, new Page.GetByLabelOptions().setExact(true)).click();
    }

    // Placeholder-based Locators
    public void getByPlaceholder(String text) {
        page.getByPlaceholder(text).click();
    }

    // TestId-based Locators
    public void getByTestId(String testId) {
        page.getByTestId(testId).click();
    }

    // Frame Operations
    public void switchToFrame(String selector) {
        page.frameLocator(selector).first();
    }

    // JavaScript Execution
    public Object evaluateJs(String javascript) {
        return page.evaluate(javascript);
    }

    // Screenshot Methods
    public void takeScreenshot(Path path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(path));
    }

    public void takeFullPageScreenshot(Path path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(path).setFullPage(true));
    }

    public void takeElementScreenshot(String selector, Path path) {
        page.locator(selector).screenshot(new Locator.ScreenshotOptions().setPath(path));
    }

    // Dialog Handlers
    public void acceptDialog() {
        page.onDialog(Dialog::accept);
    }

    public void dismissDialog() {
        page.onDialog(Dialog::dismiss);
    }

    public void acceptPrompt(String text) {
        page.onDialog(dialog -> dialog.accept(text));
    }

    // Keyboard Actions
    public void pressKey(String key) {
        page.keyboard().press(key);
    }

    public void pressKeyChord(String... keys) {
        page.keyboard().press(String.join("+", keys));
    }

    // Mouse Actions
    public void mouseMove(int x, int y) {
        page.mouse().move(x, y);
    }

    public void mouseDrag(int fromX, int fromY, int toX, int toY) {
        page.mouse().move(fromX, fromY);
        page.mouse().down();
        page.mouse().move(toX, toY);
        page.mouse().up();
    }

    // URL and Title Getters
    public String getCurrentUrl() {
        return page.url();
    }

    public String getTitle() {
        return page.title();
    }

    // Network State
    public boolean hasNetworkIdle() {
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE, 
                new Page.WaitForLoadStateOptions().setTimeout(1000));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Custom Assertions
    public void assertVisible(String selector) {
        if (!isVisible(selector)) {
            throw new AssertionError("Element not visible: " + selector);
        }
    }

    public void assertHidden(String selector) {
        if (!isHidden(selector)) {
            throw new AssertionError("Element is visible: " + selector);
        }
    }

    public void assertText(String selector, String expectedText) {
        String actualText = getText(selector);
        if (!actualText.equals(expectedText)) {
            throw new AssertionError(String.format(
                "Text mismatch for selector '%s'. Expected: '%s', Actual: '%s'",
                selector, expectedText, actualText));
        }
    }
}
