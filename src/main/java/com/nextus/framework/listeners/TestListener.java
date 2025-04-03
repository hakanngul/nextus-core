package com.nextus.framework.listeners;

import com.nextus.framework.manager.ReportManager;
import com.nextus.framework.core.DriverManager;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    
    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String testName = context.getDisplayName();
        log.info("Starting test: {}", testName);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String testName = context.getDisplayName();
        
        if (context.getExecutionException().isPresent()) {
            ReportManager.handleTestFailure(
                DriverManager.getPage(),
                testName,
                context.getExecutionException().get()
            );
        }
        
        log.info("Finished test: {}", testName);
    }
}