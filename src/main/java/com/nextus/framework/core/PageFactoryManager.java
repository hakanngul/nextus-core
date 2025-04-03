package com.nextus.framework.core;

import com.microsoft.playwright.Page;

/**
 * PageFactoryManager provides a central, thread-safe way to manage
 * and access page object instances. This is designed to be extended
 * in UI-specific modules (e.g., nextus-ui).
 */
public final class PageFactoryManager {

    private static final ThreadLocal<PageFactoryManager> factoryInstance = new ThreadLocal<>();
    private final Page page;

    private PageFactoryManager(Page page) {
        this.page = page;
    }

    public static void setPage(Page page) {
        factoryInstance.set(new PageFactoryManager(page));
    }

    public static PageFactoryManager getInstance() {
        return factoryInstance.get();
    }

    public Page getPage() {
        return this.page;
    }

    public static void remove() {
        factoryInstance.remove();
    }

    public static void close() {
        remove();
    }
}