package com.nextus.framework.tests;

import com.nextus.framework.annotations.TestTags.Smoke;
import com.nextus.framework.annotations.TestOrder;
import com.nextus.framework.base.BaseTest;
import com.nextus.framework.extensions.TestExecutionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestExecutionExtension.class)
@DisplayName("Login Functionality Tests")
class LoginTest extends BaseTest {

    @Test
    @Smoke
    @TestOrder(1)
    @DisplayName("User can login with valid credentials")
    void validLogin() {
        // Test implementation
    }

    @Test
    @TestOrder(2)
    @DisplayName("User cannot login with invalid credentials")
    void invalidLogin() {
        // Test implementation
    }
}