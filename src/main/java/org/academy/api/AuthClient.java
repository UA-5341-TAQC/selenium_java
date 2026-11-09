package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.auth.LoginData;
import org.academy.models.auth.LoginResponse;

public class AuthClient extends BaseClient {

    private static final String ENDPOINT = "/auth";
    private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    public AuthClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public Response login(String email, String password) {
        LoginData loginData = new LoginData();
        loginData.setEmail(email);
        loginData.setPassword(password);
        return postLogin(loginData);
    }

    public LoginResponse login(LoginData loginData) {
        return postLogin(loginData)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    private Response postLogin(LoginData loginData) {
        Response response = prepareRequest()
                .body(loginData)
                .post(ENDPOINT + "/login");
        extractTokens(response);
        return response;
    }

    public Response refreshToken() {
        Response response = prepareRequest()
                .post(ENDPOINT + "/refresh-token");
        extractTokens(response);
        return response;
    }

    public LoginResponse refresh() {
        return refreshToken()
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    public Response logout() {
        Response response = prepareRequest()
                .post(ENDPOINT + "/logout");
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            this.token = null;
            this.refreshToken = null;
        }
        return response;
    }

    private void extractTokens(Response response) {
        String cookie = response.getCookie(REFRESH_TOKEN_COOKIE);
        if (cookie != null) {
            this.refreshToken = cookie;
        }
        if (response.statusCode() == 200) {
            String accessToken = response.jsonPath().getString("accessToken");
            if (accessToken != null) {
                this.token = accessToken;
            }
        }
    }
}
