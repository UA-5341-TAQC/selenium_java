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
        AuthClient authClientApi = new AuthClient(testEnvProvider.getBaseApiUrl());
        Response response = authClientApi.login(testEnvProvider.getAdminEmail(), testEnvProvider.getAdminPassword());
        response.then().statusCode(200);
        response.then().body("accessToken", org.hamcrest.Matchers.notNullValue());
        Assert.assertTrue(authClientApi.getRefreshToken() != null && !authClientApi.getRefreshToken().isBlank(),
                "Refresh token should be extracted from cookie");
    }

    @Test
    public void testLogin2() {
        LoginData loginData = new LoginData();
        loginData.setEmail(testEnvProvider.getAdminEmail());
        loginData.setPassword(testEnvProvider.getAdminPassword());
        AuthClient authClientApi = new AuthClient(testEnvProvider.getBaseApiUrl());
        LoginResponse response = authClientApi.login(loginData);
        Assert.assertTrue(response.getAccessToken() != null && !response.getAccessToken().isBlank(),
                "Access token should not be null");
        Assert.assertTrue(authClientApi.getRefreshToken() != null && !authClientApi.getRefreshToken().isBlank(),
                "Refresh token should not be null");
    }
}
