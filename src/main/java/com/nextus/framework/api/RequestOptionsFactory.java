package com.nextus.framework.api;

import com.microsoft.playwright.options.RequestOptions;
import com.microsoft.playwright.options.FormData;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating Playwright RequestOptions with fluent interface
 */
public class RequestOptionsFactory {
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private Object data;
    private FormData formData;
    private Map<String, Object> multipartData;
    private Double timeout;
    private Boolean ignoreHTTPSErrors;
    private Path filePath;

    private RequestOptionsFactory() {
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
    }

    public static RequestOptionsFactory create() {
        return new RequestOptionsFactory();
    }

    public RequestOptionsFactory addHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public RequestOptionsFactory addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestOptionsFactory setAuthorization(String token) {
        return addHeader("Authorization", "Bearer " + token);
    }

    public RequestOptionsFactory setBasicAuth(String username, String password) {
        String credentials = username + ":" + password;
        String encoded = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
        return addHeader("Authorization", "Basic " + encoded);
    }

    public RequestOptionsFactory addQueryParam(String name, String value) {
        this.queryParams.put(name, value);
        return this;
    }

    public RequestOptionsFactory addQueryParams(Map<String, String> params) {
        this.queryParams.putAll(params);
        return this;
    }

    public RequestOptionsFactory setJsonData(Object data) {
        this.data = data;
        return addHeader("Content-Type", "application/json");
    }

    public RequestOptionsFactory setFormData(Map<String, String> formParams) {
        FormData formData = FormData.create();
        formParams.forEach(formData::set);
        this.formData = formData;
        return addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public RequestOptionsFactory setFormData(FormData formData) {
        this.formData = formData;
        return addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public RequestOptionsFactory setMultipartData(Map<String, Object> multipartData) {
        this.multipartData = multipartData;
        return addHeader("Content-Type", "multipart/form-data");
    }

    public RequestOptionsFactory setFile(Path filePath, String contentType) {
        this.filePath = filePath;
        return addHeader("Content-Type", contentType);
    }

    public RequestOptionsFactory setTimeout(double timeout) {
        this.timeout = timeout;
        return this;
    }

    public RequestOptionsFactory setIgnoreHTTPSErrors(boolean ignore) {
        this.ignoreHTTPSErrors = ignore;
        return this;
    }

    public RequestOptions build() {
        RequestOptions options = RequestOptions.create();

        // Add headers
        headers.forEach(options::setHeader);

        // Add query parameters
        queryParams.forEach(options::setQueryParam);

        // Set data based on type
        if (data != null) {
            options.setData(data);
        } else if (formData != null) {
            options.setForm(formData);
        } else if (multipartData != null) {
            options.setMultipart((FormData) multipartData);
        } else if (filePath != null) {
            options.setData(filePath);
        }

        // Set timeout if specified
        if (timeout != null) {
            options.setTimeout(timeout);
        }

        // Set HTTPS error handling if specified
        if (ignoreHTTPSErrors != null) {
            options.setIgnoreHTTPSErrors(ignoreHTTPSErrors);
        }

        return options;
    }

    // Convenience factory methods
    public static RequestOptionsFactory json() {
        return create()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json");
    }

    public static RequestOptionsFactory jsonWithAuth(String token) {
        return json().setAuthorization(token);
    }

    public static RequestOptionsFactory form() {
        return create()
                .addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public static RequestOptionsFactory multipart() {
        return create()
                .addHeader("Content-Type", "multipart/form-data");
    }
}
