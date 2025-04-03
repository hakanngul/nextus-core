package com.nextus.framework.tests;

import com.nextus.framework.annotations.TestTags;
import com.nextus.framework.annotations.TestTags.*;
import com.nextus.framework.base.BaseApiTest;
import com.nextus.framework.data.model.UserModel;
import com.nextus.framework.extensions.TestExecutionExtension;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.nextus.framework.assertions.ApiAssert;
import io.qameta.allure.Feature;
@ExtendWith(TestExecutionExtension.class)
@Feature("Login")
class LoginTest extends BaseApiTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Smoke
    @DisplayName("User can login with valid credentials")
    void validLogin() {
        // Arrange - Simulating login with existing user (ID: 1)
        APIResponse response = request.get(BASE_URL + "/users/1");

        // Assert
        ApiAssert.assertThat(response)
            .isSuccess()
            .isJson()
            .hasField("email", "Sincere@april.biz")
            .hasField("username", "Bret");
    }

    @Regression
    @DisplayName("User cannot login with invalid credentials")
    void invalidLogin() {
        // Arrange - Trying to get non-existent user (ID: 999)
        APIResponse response = request.get(BASE_URL + "/users/999");

        // Assert
        ApiAssert.assertThat(response)
            .hasStatus(404)
            .hasEmptyBody();
    }

    @API
    @DisplayName("User can login with new registration")
    void loginWithNewRegistration() {
        // Arrange
        UserModel newUser = UserModel.builder()
            .username("testuser")
            .email("test@example.com")
            .name("Test User")
            .build();

        // Act - Simulate registration by creating new user
        APIResponse response = request.post(BASE_URL + "/users",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(newUser));

        // Assert
        ApiAssert.assertThat(response)
            .isCreated()
            .isJson()
            .bodyEquals(newUser);
    }
}