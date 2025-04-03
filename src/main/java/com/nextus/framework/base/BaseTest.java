package com.nextus.framework.base;

import com.microsoft.playwright.Page;
import com.nextus.framework.core.DriverManager;
import com.nextus.framework.core.PageFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * BaseTest provides test lifecycle hooks for web tests.
 * It initializes the page and page factory before each test,
 * and performs cleanup after each test.
 */
public abstract class BaseTest {

    protected Page page;
    protected PageFactoryManager pageFactory;

    @BeforeEach
    public void setUp() {
        page = DriverManager.getPage();
        PageFactoryManager.setPage(page);
        pageFactory = PageFactoryManager.getInstance();
    }

    @AfterEach
    public void tearDown() {
        DriverManager.close();
        PageFactoryManager.remove();
    }
}