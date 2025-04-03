package com.nextus.framework.api;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;

/**
 * Manages API requests using Playwright's APIRequestContext
 */
public class ApiRequestManager {
    private final APIRequestContext context;
    private final Gson gson;

    public ApiRequestManager(APIRequestContext context) {
        this.context = context;
        this.gson = new Gson();
    }

    public APIResponse get(String url) {
        return context.get(url);
    }

    public APIResponse get(String url, RequestOptions options) {
        return context.get(url, options);
    }

    public APIResponse post(String url, RequestOptions options) {
        return context.post(url, options);
    }

    public APIResponse put(String url, RequestOptions options) {
        return context.put(url, options);
    }

    public APIResponse patch(String url, RequestOptions options) {
        return context.patch(url, options);
    }

    public APIResponse delete(String url, RequestOptions options) {
        return context.delete(url, options);
    }

    public <T> T parseResponse(APIResponse response, Class<T> clazz) {
        try {
            String json = response.text();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response to " + clazz.getSimpleName(), e);
        }
    }

    // Convenience methods for common request patterns
    public APIResponse getJson(String url) {
        return get(url, RequestOptionsFactory.json().build());
    }

    public APIResponse getJsonWithAuth(String url, String token) {
        return get(url, RequestOptionsFactory.jsonWithAuth(token).build());
    }

    public APIResponse postJson(String url, Object data) {
        return post(url, RequestOptionsFactory.json()
                .setJsonData(data)
                .build());
    }

    public APIResponse postJsonWithAuth(String url, Object data, String token) {
        return post(url, RequestOptionsFactory.jsonWithAuth(token)
                .setJsonData(data)
                .build());
    }

    public APIResponse postForm(String url, Map<String, String> formData) {
        return post(url, RequestOptionsFactory.form()
                .setFormData(formData)
                .build());
    }

    public void dispose() {
        if (context != null) {
            context.dispose();
        }
    }
}