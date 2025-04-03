package com.nextus.framework.assertions;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import org.junit.jupiter.api.Assertions;

public class NetworkAssert {
    private final Page page;
    private final NextusAssert root;

    NetworkAssert(Page page, NextusAssert root) {
        this.page = page;
        this.root = root;
    }

    public NextusAssert and() {
        return root;
    }

    public NetworkAssert responseOk(String url) {
        Response response = page.waitForResponse(url, () -> {});
        Assertions.assertTrue(response.ok(),
            String.format("Response for '%s' should be OK", url));
        return this;
    }

    public NetworkAssert responseStatus(String url, int expectedStatus) {
        Response response = page.waitForResponse(url, () -> {});
        Assertions.assertEquals(expectedStatus, response.status(),
            String.format("Response status for '%s' mismatch", url));
        return this;
    }

    public NetworkAssert responseBodyContains(String url, String expectedContent) {
        Response response = page.waitForResponse(url, () -> {});
        String body = response.text();
        Assertions.assertTrue(body.contains(expectedContent),
            String.format("Response body for '%s' should contain '%s'", url, expectedContent));
        return this;
    }
}