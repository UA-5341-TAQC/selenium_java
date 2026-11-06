package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.auth.LoginData;
import org.academy.models.auth.LoginResponse;

public class AuthClient extends BaseClient {

    private static final String ENDPOINT = "/auth";

    public AuthClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public Response login(String email, String password) {
        return prepareRequest()
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .post(ENDPOINT + "/login");
    }
    public LoginResponse login(LoginData loginData) {
        return prepareRequest()
                .body(loginData)
                .post(ENDPOINT + "/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }
}
