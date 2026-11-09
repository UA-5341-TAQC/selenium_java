package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.academy.models.bankdetails.BankCurrency;
import org.academy.models.bankdetails.ForeignBankDetailsDto;
import org.academy.models.bankdetails.SupportOptionDto;
import org.academy.models.bankdetails.UahBankDetailsDto;

import java.util.List;

public class BankDetailsClient extends BaseClient {

    private static final String UAH_ENDPOINT = "/UahBankDetails";
    private static final String UAH_PUBLISHED_ENDPOINT = UAH_ENDPOINT + "/published";
    private static final String FOREIGN_ENDPOINT = "/ForeignBankDetails";
    private static final String FOREIGN_PUBLISHED_ENDPOINT = FOREIGN_ENDPOINT + "/published";
    private static final String SUPPORT_ENDPOINT = "/SupportOptions";
    private static final String SUPPORT_PUBLISHED_ENDPOINT = SUPPORT_ENDPOINT + "/published";

    public BankDetailsClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public BankDetailsClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    // --- UAH Bank Details ---

    public Response getPublishedUahBankDetailsResponse() {
        return prepareRequest().get(UAH_PUBLISHED_ENDPOINT);
    }

    public List<UahBankDetailsDto> getPublishedUahBankDetails() {
        return getPublishedUahBankDetailsResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", UahBankDetailsDto.class);
    }

    public Response getUahBankDetailsResponse() {
        return prepareRequest().get(UAH_ENDPOINT);
    }

    public List<UahBankDetailsDto> getUahBankDetails() {
        return getUahBankDetailsResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", UahBankDetailsDto.class);
    }

    // --- Foreign Bank Details ---

    public Response getPublishedForeignBankDetailsResponse() {
        return getPublishedForeignBankDetailsResponse((Integer) null);
    }

    public Response getPublishedForeignBankDetailsResponse(BankCurrency currency) {
        return getPublishedForeignBankDetailsResponse(currency != null ? currency.getValue() : null);
    }

    public Response getPublishedForeignBankDetailsResponse(Integer currency) {
        RequestSpecification req = prepareRequest();
        if (currency != null) {
            req.queryParam("currency", currency);
        }
        return req.get(FOREIGN_PUBLISHED_ENDPOINT);
    }

    public List<ForeignBankDetailsDto> getPublishedForeignBankDetails() {
        return getPublishedForeignBankDetails((BankCurrency) null);
    }

    public List<ForeignBankDetailsDto> getPublishedForeignBankDetails(BankCurrency currency) {
        return getPublishedForeignBankDetailsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ForeignBankDetailsDto.class);
    }

    public List<ForeignBankDetailsDto> getPublishedForeignBankDetails(Integer currency) {
        return getPublishedForeignBankDetailsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ForeignBankDetailsDto.class);
    }

    public Response getForeignBankDetailsResponse() {
        return getForeignBankDetailsResponse((Integer) null);
    }

    public Response getForeignBankDetailsResponse(BankCurrency currency) {
        return getForeignBankDetailsResponse(currency != null ? currency.getValue() : null);
    }

    public Response getForeignBankDetailsResponse(Integer currency) {
        RequestSpecification req = prepareRequest();
        if (currency != null) {
            req.queryParam("currency", currency);
        }
        return req.get(FOREIGN_ENDPOINT);
    }

    public List<ForeignBankDetailsDto> getForeignBankDetails() {
        return getForeignBankDetails((BankCurrency) null);
    }

    public List<ForeignBankDetailsDto> getForeignBankDetails(BankCurrency currency) {
        return getForeignBankDetailsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ForeignBankDetailsDto.class);
    }

    public List<ForeignBankDetailsDto> getForeignBankDetails(Integer currency) {
        return getForeignBankDetailsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ForeignBankDetailsDto.class);
    }

    // --- Support Options ---

    public Response getPublishedSupportOptionsResponse() {
        return getPublishedSupportOptionsResponse((Integer) null);
    }

    public Response getPublishedSupportOptionsResponse(BankCurrency currency) {
        return getPublishedSupportOptionsResponse(currency != null ? currency.getValue() : null);
    }

    public Response getPublishedSupportOptionsResponse(Integer currency) {
        RequestSpecification req = prepareRequest();
        if (currency != null) {
            req.queryParam("currency", currency);
        }
        return req.get(SUPPORT_PUBLISHED_ENDPOINT);
    }

    public List<SupportOptionDto> getPublishedSupportOptions() {
        return getPublishedSupportOptions((BankCurrency) null);
    }

    public List<SupportOptionDto> getPublishedSupportOptions(BankCurrency currency) {
        return getPublishedSupportOptionsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", SupportOptionDto.class);
    }

    public List<SupportOptionDto> getPublishedSupportOptions(Integer currency) {
        return getPublishedSupportOptionsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", SupportOptionDto.class);
    }

    public Response getSupportOptionsResponse() {
        return getSupportOptionsResponse((Integer) null);
    }

    public Response getSupportOptionsResponse(BankCurrency currency) {
        return getSupportOptionsResponse(currency != null ? currency.getValue() : null);
    }

    public Response getSupportOptionsResponse(Integer currency) {
        RequestSpecification req = prepareRequest();
        if (currency != null) {
            req.queryParam("currency", currency);
        }
        return req.get(SUPPORT_ENDPOINT);
    }

    public List<SupportOptionDto> getSupportOptions() {
        return getSupportOptions((BankCurrency) null);
    }

    public List<SupportOptionDto> getSupportOptions(BankCurrency currency) {
        return getSupportOptionsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", SupportOptionDto.class);
    }

    public List<SupportOptionDto> getSupportOptions(Integer currency) {
        return getSupportOptionsResponse(currency)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", SupportOptionDto.class);
    }
}
