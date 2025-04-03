package com.nextus.framework.assertions;

import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.nextus.framework.data.model.UserModel;
import org.junit.jupiter.api.Assertions;

public class NextusAssert {
    private final Page page;
    private final ElementAssert elementAssert;
    private final PageAssert pageAssert;
    private final NetworkAssert networkAssert;
    private final ApiAssert apiAssert;
    private final Gson gson = new Gson();


    private NextusAssert(Page page) {
        this.page = page;
        this.elementAssert = new ElementAssert(page, this); // this referansını geçir
        this.pageAssert = new PageAssert(page, this);
        this.networkAssert = new NetworkAssert(page, this);
        this.apiAssert = new ApiAssert(null, this); // Default constructor
    }

    public static NextusAssert  assertThat(Page page) {
        return new NextusAssert(page);
    }

    public static ApiAssert assertThat(APIResponse response) {
        return new ApiAssert(response, null);
    }

    public ElementAssert element(String selector) {
        return elementAssert.withSelector(selector);
    }

    public ElementAssert element(Locator locator) {
        return elementAssert.withLocator(locator);
    }

    public PageAssert page() {
        return pageAssert;
    }

    public NetworkAssert network() {
        return networkAssert;
    }

    public ApiAssert api() {
        if (apiAssert == null) {
            throw new IllegalStateException("API assertions are only available for APIResponse objects");
        }
        return apiAssert;
    }
}
