package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.bankdetails.BankCurrency;
import org.academy.models.bankdetails.ForeignBankDetailsDto;
import org.academy.models.bankdetails.ProblemDetails;
import org.academy.models.bankdetails.SupportOptionDto;
import org.academy.models.bankdetails.UahBankDetailsDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class BankDetailsTests extends BaseAPITestRunner {

    private BankDetailsClient publicClient;
    private BankDetailsClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new BankDetailsClient(testEnvProvider.getBaseApiUrl());
        adminClient = new BankDetailsClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetPublishedUahBankDetails() {
        Response response = publicClient.getPublishedUahBankDetailsResponse();
        response.then().statusCode(200);

        List<UahBankDetailsDto> list = publicClient.getPublishedUahBankDetails();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(list, "UAH bank details list should not be null");
        softAssert.assertFalse(list.isEmpty(), "UAH bank details list should not be empty");

        UahBankDetailsDto first = list.get(0);
        softAssert.assertNotNull(first.getId(), "Id should not be null");
        softAssert.assertNotNull(first.getName(), "Name should not be null");
        softAssert.assertNotNull(first.getReceiver(), "Receiver should not be null");
        softAssert.assertNotNull(first.getEdrpou(), "EDRPOU should not be null");
        softAssert.assertNotNull(first.getUkrainianIban(), "Ukrainian IBAN should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetUahBankDetailsAuthenticated() {
        Response response = adminClient.getUahBankDetailsResponse();
        response.then().statusCode(200);

        List<UahBankDetailsDto> list = adminClient.getUahBankDetails();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(list, "Authenticated UAH bank details list should not be null");
        softAssert.assertFalse(list.isEmpty(), "Authenticated UAH bank details list should not be empty");

        softAssert.assertAll();
    }

    @Test
    public void testGetPublishedForeignBankDetails() {
        Response responseWithoutCur = publicClient.getPublishedForeignBankDetailsResponse();
        responseWithoutCur.then().statusCode(200);

        Response responseUsd = publicClient.getPublishedForeignBankDetailsResponse(BankCurrency.USD);
        responseUsd.then().statusCode(200);

        List<ForeignBankDetailsDto> listUsd = publicClient.getPublishedForeignBankDetails(BankCurrency.USD);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(listUsd, "USD Foreign bank details list should not be null");
        softAssert.assertFalse(listUsd.isEmpty(), "USD Foreign bank details list should not be empty");

        ForeignBankDetailsDto first = listUsd.get(0);
        softAssert.assertNotNull(first.getId(), "Id should not be null");
        softAssert.assertNotNull(first.getName(), "Name should not be null");
        softAssert.assertNotNull(first.getReceiver(), "Receiver should not be null");
        softAssert.assertNotNull(first.getUkrainianIban(), "Ukrainian IBAN should not be null");
        softAssert.assertNotNull(first.getSwift(), "SWIFT should not be null");
        softAssert.assertEquals(first.getCurrency(), BankCurrency.USD, "Currency should be USD");
        softAssert.assertNotNull(first.getCorrespondentBanks(), "Correspondent banks should not be null");
        softAssert.assertFalse(first.getCorrespondentBanks().isEmpty(), "Correspondent banks should not be empty");

        softAssert.assertAll();
    }

    @Test
    public void testGetForeignBankDetailsAuthenticated() {
        Response response = adminClient.getForeignBankDetailsResponse(BankCurrency.EUR);
        response.then().statusCode(200);

        List<ForeignBankDetailsDto> listEur = adminClient.getForeignBankDetails(BankCurrency.EUR);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(listEur, "EUR Foreign bank details list should not be null");
        softAssert.assertFalse(listEur.isEmpty(), "EUR Foreign bank details list should not be empty");

        ForeignBankDetailsDto first = listEur.get(0);
        softAssert.assertEquals(first.getCurrency(), BankCurrency.EUR, "Currency should be EUR");
        softAssert.assertNotNull(first.getCorrespondentBanks(), "Correspondent banks should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetPublishedSupportOptions() {
        Response responseAll = publicClient.getPublishedSupportOptionsResponse();
        responseAll.then().statusCode(200);

        List<SupportOptionDto> listAll = publicClient.getPublishedSupportOptions();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(listAll, "Published support options list should not be null");
        softAssert.assertFalse(listAll.isEmpty(), "Published support options list should not be empty");

        List<SupportOptionDto> listUah = publicClient.getPublishedSupportOptions(BankCurrency.UAH);
        softAssert.assertNotNull(listUah, "UAH support options list should not be null");
        softAssert.assertFalse(listUah.isEmpty(), "UAH support options list should not be empty");

        SupportOptionDto first = listUah.get(0);
        softAssert.assertNotNull(first.getId(), "Id should not be null");
        softAssert.assertNotNull(first.getName(), "Name should not be null");
        softAssert.assertNotNull(first.getValue(), "Value should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetSupportOptionsAuthenticated() {
        Response response = adminClient.getSupportOptionsResponse(BankCurrency.UAH);
        response.then().statusCode(200);

        List<SupportOptionDto> list = adminClient.getSupportOptions(BankCurrency.UAH);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(list, "Authenticated support options list should not be null");
        softAssert.assertFalse(list.isEmpty(), "Authenticated support options list should not be empty");

        softAssert.assertAll();
    }

    @Test
    public void testGetForeignBankDetailsWithInvalidCurrencyReturnsProblemDetails() {
        Response response = publicClient.getPublishedForeignBankDetailsResponse(9999);
        response.then().statusCode(400);

        ProblemDetails problem = response.as(ProblemDetails.class);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(problem, "ProblemDetails should not be null");
        softAssert.assertEquals(problem.getStatus(), Integer.valueOf(400), "Status code should be 400");
        softAssert.assertNotNull(problem.getTitle(), "Title should not be null");

        softAssert.assertAll();
    }
}
