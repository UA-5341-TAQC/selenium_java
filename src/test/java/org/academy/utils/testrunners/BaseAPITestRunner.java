package org.academy.utils.testrunners;

import org.academy.api.AuthClient;
import org.academy.models.auth.LoginData;
import org.academy.models.auth.LoginResponse;


public class BaseAPITestRunner extends BaseTestRunner {

    protected String getAccessToken() {
        LoginData loginData = new LoginData();
        loginData.setEmail(testEnvProvider.getAdminEmail());
        loginData.setPassword(testEnvProvider.getAdminPassword());
        AuthClient authClientApi = new AuthClient(testEnvProvider.getBaseApiUrl());
        LoginResponse response = authClientApi.login(loginData);
        return response.getAccessToken();
    }
}
