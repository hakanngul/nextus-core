package com.nextus.framework.network;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
public class NetworkInterceptor {
    private final Page page;

    public NetworkInterceptor(Page page) {
        this.page = page;
    }

    /**
     * Mocks an API response for a specific URL pattern
     *
     * @param urlPattern URL pattern to match
     * @param responseBody Response body to return
     * @param statusCode HTTP status code (default 200)
     * @param headers Response headers (optional)
     */
    public void mockApiResponse(String urlPattern, String responseBody, int statusCode, Map<String, String> headers) {
        page.route(urlPattern, route -> {
            Route.FulfillOptions options = new Route.FulfillOptions()
                    .setBody(responseBody)
                    .setStatus(statusCode);
            
            if (headers != null) {
                options.setHeaders(headers);
            }
            
            route.fulfill(options);
            log.debug("Mocked response for URL pattern: {}", urlPattern);
        });
    }

    /**
     * Blocks requests matching the given URL pattern
     */
    public void blockRequests(String urlPattern) {
        page.route(urlPattern, Route::abort);
        log.debug("Blocking requests for pattern: {}", urlPattern);
    }

    /**
     * Captures and logs network requests
     */
    public void captureRequests(String urlPattern, Consumer<Request> requestHandler) {
        page.onRequest(request -> {
            if (request.url().contains(urlPattern)) {
                requestHandler.accept(request);
                log.debug("Captured request: {}", request.url());
            }
        });
    }

    /**
     * Captures and logs network responses
     */
    public void captureResponses(String urlPattern, Consumer<Response> responseHandler) {
        page.onResponse(response -> {
            if (response.url().contains(urlPattern)) {
                responseHandler.accept(response);
                log.debug("Captured response: {} - Status: {}", 
                    response.url(), response.status());
            }
        });
    }

    /**
     * Waits for a network request matching the pattern with timeout
     *
     * @param urlPattern URL pattern to match
     * @param timeout Timeout in milliseconds
     * @return Matched Request object
     */
    public Request waitForRequest(String urlPattern, int timeout) {
        try {
            return page.waitForRequest(urlPattern,
                    (Runnable) new Page.WaitForRequestOptions().setTimeout(timeout));
        } catch (Exception e) {
            log.error("Timeout waiting for request: {}", urlPattern);
            throw new RuntimeException("Request timeout for pattern: " + urlPattern, e);
        }
    }

    /**
     * Waits for a network request matching the pattern with default timeout
     *
     * @param urlPattern URL pattern to match
     * @return Matched Request object
     */
    public Request waitForRequest(String urlPattern) {
        return waitForRequest(urlPattern, 30000); // 30 seconds default timeout
    }

    /**
     * Waits for a network request matching a predicate
     *
     * @param predicate Predicate to match the request
     * @param timeout Timeout in milliseconds
     * @return Matched Request object
     */
    public Request waitForRequest(Predicate<Request> predicate, int timeout) {
        try {
            return page.waitForRequest(predicate,
                    (Runnable) new Page.WaitForRequestOptions().setTimeout(timeout));
        } catch (Exception e) {
            log.error("Timeout waiting for request with predicate");
            throw new RuntimeException("Request timeout for predicate", e);
        }
    }

    /**
     * Waits for a network response matching the pattern with timeout
     *
     * @param urlPattern URL pattern to match
     * @param timeout Timeout in milliseconds
     * @return Matched Response object
     */
    public Response waitForResponse(String urlPattern, int timeout) {
        try {
            return page.waitForResponse(urlPattern,
                    (Runnable) new Page.WaitForResponseOptions().setTimeout(timeout));
        } catch (Exception e) {
            log.error("Timeout waiting for response: {}", urlPattern);
            throw new RuntimeException("Response timeout for pattern: " + urlPattern, e);
        }
    }

    /**
     * Waits for a network response matching the pattern with default timeout
     *
     * @param urlPattern URL pattern to match
     * @return Matched Response object
     */
    public Response waitForResponse(String urlPattern) {
        return waitForResponse(urlPattern, 30000); // 30 seconds default timeout
    }

    /**
     * Waits for a network response matching a predicate
     *
     * @param predicate Predicate to match the response
     * @param timeout Timeout in milliseconds
     * @return Matched Response object
     */
    public Response waitForResponse(Predicate<Response> predicate, int timeout) {
        try {
            return page.waitForResponse(predicate,
                    (Runnable) new Page.WaitForResponseOptions().setTimeout(timeout));
        } catch (Exception e) {
            log.error("Timeout waiting for response with predicate");
            throw new RuntimeException("Response timeout for predicate", e);
        }
    }
}