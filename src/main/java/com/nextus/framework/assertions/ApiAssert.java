package com.nextus.framework.assertions;

import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import com.jayway.jsonpath.JsonPath;
import com.nextus.framework.data.model.UserModel;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ApiAssert {
    private final APIResponse response;
    private final NextusAssert root;
    private final Gson gson;
    private final String responseBody;

    ApiAssert(APIResponse response, NextusAssert root) {
        this.response = response;
        this.root = root;
        this.gson = new Gson();
        this.responseBody = response.text();
    }

    public static ApiAssert assertThat(APIResponse response) {
        return new ApiAssert(response, null);
    }

    // JsonPath metodunu daha okunabilir hale getirelim
    public ApiAssert hasField(String field, Object expectedValue) {
        return jsonPath("$." + field, expectedValue);
    }

    public ApiAssert isCreated() {
        return hasStatus(201);
    }

    public ApiAssert isSuccess() {
        return hasStatus(200);
    }


    public ApiAssert isNotSuccess() {
        return hasStatusNot();
    }

    private ApiAssert hasStatusNot() {
        Assertions.assertNotEquals(200, response.status(),
            String.format("Expected status not %d but got %d", 200, response.status()));
        return this;
    }

    public ApiAssert hasStatus(int expectedStatus) {
        Assertions.assertEquals(expectedStatus, response.status(),
            String.format("Expected status %d but got %d", expectedStatus, response.status()));
        return this;
    }

    public ApiAssert isJson() {
        String contentType = response.headers().get("content-type");
        Assertions.assertTrue(
            contentType != null && contentType.toLowerCase().contains("application/json"),
            "Expected content type to be JSON but was: " + contentType
        );
        return this;
    }

    public ApiAssert hasHeader(String name, String expectedValue) {
        String actualValue = response.headers().get(name);
        Assertions.assertEquals(expectedValue, actualValue,
            String.format("Header '%s' mismatch. Expected: '%s', Actual: '%s'",
                name, expectedValue, actualValue));
        return this;
    }

    public ApiAssert bodyEquals(UserModel expected) {
        UserModel actual = gson.fromJson(response.text(), UserModel.class);
        Assertions.assertEquals(expected.getName(), actual.getName(), "Name mismatch");
        Assertions.assertEquals(expected.getEmail(), actual.getEmail(), "Email mismatch");
        Assertions.assertEquals(expected.getUsername(), actual.getUsername(), "Username mismatch");
        Assertions.assertEquals(expected.getPhone(), actual.getPhone(), "Phone mismatch");
        Assertions.assertEquals(expected.getWebsite(), actual.getWebsite(), "Website mismatch");
        return this;
    }

    public ApiAssert hasEmptyBody() {
        String body = response.text();
        Assertions.assertTrue(body == null || body.isEmpty(), "Expected empty response body");
        return this;
    }

    public ApiAssert jsonPath(String path, Object expectedValue) {
        Object actual = JsonPath.read(responseBody, path);
        Assertions.assertEquals(expectedValue, actual,
            String.format("JsonPath '%s' mismatch. Expected: '%s', Actual: '%s'",
                path, expectedValue, actual));
        return this;
    }

    public ApiAssert jsonPathIsArray(String path) {
        Object value = JsonPath.read(responseBody, path);
        Assertions.assertTrue(value instanceof List,
            String.format("Expected JsonPath '%s' to be an array", path));
        return this;
    }

    public ApiAssert jsonPathArrayHasSize(String path, int expectedSize) {
        List<?> array = JsonPath.read(responseBody, path);
        Assertions.assertEquals(expectedSize, array.size(),
            String.format("Expected JsonPath '%s' array to have size %d", path, expectedSize));
        return this;
    }

    public ApiAssert jsonPathExists(String path) {
        Object value = JsonPath.read(responseBody, path);
        Assertions.assertNotNull(value,
            String.format("Expected JsonPath '%s' to exist", path));
        return this;
    }

    public NextusAssert and() {
        return root;
    }
}
