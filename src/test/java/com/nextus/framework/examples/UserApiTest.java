package com.nextus.framework.examples;

import com.nextus.framework.annotations.ApiTest;
import com.nextus.framework.base.BaseApiTest;
import com.nextus.framework.data.model.UserModel;
import com.nextus.framework.data.generator.JavaFakerUtils;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import com.nextus.framework.assertions.ApiAssert;
import io.qameta.allure.*;

@ApiTest
@Epic("API Tests")
@Feature("User Management")
@Execution(ExecutionMode.CONCURRENT)
public class UserApiTest extends BaseApiTest {
    
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    @Story("Create User")
    @Description("Test creating a new user via API")
    @Severity(SeverityLevel.CRITICAL)
    void shouldCreateNewUser() {
        // Arrange
        UserModel newUser = UserModel.builder()
            .name(JavaFakerUtils.randomFullName())
            .email(JavaFakerUtils.randomEmail())
            .username(JavaFakerUtils.randomUsername())
            .phone(JavaFakerUtils.randomPhoneNumber())
            .website(JavaFakerUtils.randomUrl())
            .build();

        // Act
        APIResponse response = request.post(BASE_URL + "/users",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(newUser));

        // Assert
        ApiAssert.assertThat(response)
            .isCreated()
            .isJson()
            .bodyEquals(newUser);

        // Add response to Allure report
        Allure.attachment("Response", response.text());
    }

    @Test
    void shouldRetrieveUserDetails() {
        // Act
        APIResponse response = request.get(BASE_URL + "/users/1");

        // Assert
        ApiAssert.assertThat(response)
            .isSuccess()
            .isJson()
            .hasField("name", "Leanne Graham")
            .hasField("username", "Bret")
            .hasField("email", "Sincere@april.biz");
    }

    @Test
    void shouldUpdateUserDetails() {
        // Arrange
        UserModel updateUser = UserModel.builder()
            .name(JavaFakerUtils.randomFullName())
            .email(JavaFakerUtils.randomEmail())
            .build();

        // Act
        APIResponse response = request.put(BASE_URL + "/users/1",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(updateUser));

        // Assert
        ApiAssert.assertThat(response)
            .isSuccess()
            .isJson()
            .bodyEquals(updateUser);
    }

    @Test
    void shouldDeleteUser() {
        // Act
        APIResponse response = request.delete(BASE_URL + "/users/1");

        // Assert
        ApiAssert.assertThat(response)
            .isSuccess();
    }
}