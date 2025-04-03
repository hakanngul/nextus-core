package com.nextus.framework.network;

import com.microsoft.playwright.Page;
import com.nextus.framework.base.BaseTest;
import com.nextus.framework.core.DriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Network Interceptor Tests")
class NetworkInterceptorTest extends BaseTest {
    private NetworkInterceptor interceptor;
    private Page page;

    @BeforeEach
    public void setUp() {
        page = DriverManager.getPage();
        interceptor = new NetworkInterceptor(page);
    }

    @Test
    @DisplayName("Should mock API response")
    void shouldMockApiResponse() {
        // Arrange
        String mockResponse = "{\"status\": \"success\"}";
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        // Act
        interceptor.mockApiResponse("/api/test", mockResponse, 200, headers);

        // Assert
        page.navigate("https://example.com");
        // Add assertions for mocked response
    }

    @Test
    @DisplayName("Should block requests")
    void shouldBlockRequests() {
        // Arrange
        AtomicBoolean requestBlocked = new AtomicBoolean(false);

        // Act
        interceptor.blockRequests("**/analytics/**");
        interceptor.captureRequests("**/analytics/**", 
            request -> requestBlocked.set(true));

        // Assert
        page.navigate("https://example.com");
        assertFalse(requestBlocked.get(), "Request should be blocked");
    }

    @Test
    @DisplayName("Should capture requests")
    void shouldCaptureRequests() {
        // Arrange
        AtomicBoolean requestCaptured = new AtomicBoolean(false);

        // Act
        interceptor.captureRequests("**", 
            request -> requestCaptured.set(true));

        // Assert
        page.navigate("https://example.com");
        assertTrue(requestCaptured.get(), "Request should be captured");
    }
}