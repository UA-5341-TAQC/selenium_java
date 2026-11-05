package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.auth.LoginData;
import org.academy.models.auth.LoginResponse;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthTests extends BaseAPITestRunner {

    @Test
    public void testLogin() {
        AuthClient authClient = new AuthClient(testEnvProvider.getBaseUrl());
        AuthClient authClientApi = new AuthClient(testEnvProvider.getBaseApiUrl());
        Response response = authClientApi.login(testEnvProvider.getAdminEmail(), testEnvProvider.getAdminPassword());
        // Add assertions here
        // Example assertion
        response.then().statusCode(200);
        response.then().body("accessToken", org.hamcrest.Matchers.notNullValue());
    }
    @Test
    public void testLogin2() {
        AuthClient authClient = new AuthClient(testEnvProvider.getBaseUrl());
        LoginData loginData = new LoginData();
        loginData.setEmail(testEnvProvider.getAdminEmail());
        loginData.setPassword(testEnvProvider.getAdminPassword());
        AuthClient authClientApi = new AuthClient(testEnvProvider.getBaseApiUrl());
        LoginResponse response = authClientApi.login(loginData);
        Assert.assertNotNull(response.getAccessToken(), "Access token should not be null");
    }
}
