package com.nextus.framework.retry;

import com.nextus.framework.config.FrameworkConfig;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryAnalyzer implements TestExecutionExceptionHandler {
    
    private static final int MAX_RETRIES = 2;
    private final ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (retryCount.get() < MAX_RETRIES) {
            retryCount.set(retryCount.get() + 1);
            log.info("Retrying test: {} - Attempt: {}", context.getDisplayName(), retryCount.get());
            // Re-run the test
            context.getRequiredTestMethod().invoke(context.getRequiredTestInstance());
        } else {
            throw throwable;
        }
    }
}