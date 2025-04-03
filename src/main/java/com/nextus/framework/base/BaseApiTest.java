package com.nextus.framework.base;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.nextus.framework.core.PlaywrightManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * BaseApiTest provides thread-safe setup for API testing using Playwright's native APIRequestContext.
 * Each test thread receives an isolated context for independent API calls.
 */
public abstract class BaseApiTest {

    protected APIRequestContext request;

    @BeforeEach
    public void setUpApiContext() {
        Playwright playwright = PlaywrightManager.getPlaywright();
        request = playwright.request().newContext();
    }

    @AfterEach
    public void tearDownApiContext() {
        if (request != null) {
            request.dispose();
            request = null;
        }
        PlaywrightManager.close();
    }
}