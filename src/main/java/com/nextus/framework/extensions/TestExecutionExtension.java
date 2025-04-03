package com.nextus.framework.extensions;

import com.nextus.framework.manager.ReportManager;
import com.nextus.framework.core.DriverManager;
import org.junit.jupiter.api.extension.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestExecutionExtension implements 
        BeforeAllCallback, 
        AfterAllCallback,
        BeforeEachCallback, 
        AfterEachCallback, 
        TestExecutionExceptionHandler {

    @Override
    public void beforeAll(ExtensionContext context) {
        log.info("Starting test class: {}", context.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        log.info("Finished test class: {}", context.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        log.info("Starting test: {}", context.getDisplayName());
        // Test başlangıç zamanını kaydet
        context.getStore(ExtensionContext.Namespace.create(getClass()))
                .put("start-time", System.currentTimeMillis());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        // Test süresini hesapla
        long startTime = context.getStore(ExtensionContext.Namespace.create(getClass()))
                .get("start-time", long.class);
        long duration = System.currentTimeMillis() - startTime;
        
        log.info("Test {} completed in {} ms", context.getDisplayName(), duration);
        
        // Cleanup
        DriverManager.close();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        ReportManager.handleTestFailure(DriverManager.getPage(), context.getDisplayName(), throwable);
        throw throwable;
    }
}