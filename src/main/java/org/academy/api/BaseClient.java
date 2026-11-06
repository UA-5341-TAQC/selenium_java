package org.academy.api;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;


public class BaseClient {
    protected final String baseAPIUrl;
    protected final ContentType contentType;

    @Getter
    @Setter
    protected String token;

    @Getter
    @Setter
    protected String refreshToken;

    public BaseClient(String baseAPIUrl) {
        this.baseAPIUrl = baseAPIUrl;
        this.contentType = ContentType.JSON;
    }

    public BaseClient(String baseAPIUrl, ContentType contentType) {
        this.baseAPIUrl = baseAPIUrl;
        this.contentType = contentType;
    }

    protected RequestSpecification prepareRequest() {
        RequestSpecification request = RestAssured.given()
                .config(RestAssuredConfig.config().logConfig(
                        LogConfig.logConfig().blacklistHeader("Authorization")
                ))
                .baseUri(baseAPIUrl)
                .contentType(contentType);
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        if (refreshToken != null) {
            request.cookie("refreshToken", refreshToken);
        }
        return request;
    }
}
