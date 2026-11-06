package org.academy.api;

import io.restassured.response.Response;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ImageTests extends BaseAPITestRunner {

    protected ImageClient imageClient;

    @BeforeClass
    public void beforeClass() {
        imageClient = new ImageClient(testEnvProvider.getBaseApiUrl());
        imageClient.setToken(getAccessToken());
    }

    @DataProvider(name = "imageIds")
    public Object[][] imageIds() {
        return new Object[][]{
                {2L, 200, 2, "image/jpeg", null},
                {999L, 404, null, null, "Resource was not found."}
        };
    }

    @Test(dataProvider = "imageIds")
    public void testGetImageById(Long id, int expectedStatusCode, Integer expectedId, String expectedMimeType, String expectedErrorMessage) {

        Response response = imageClient.getImageById(id);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.statusCode(), expectedStatusCode);
        softAssert.assertEquals(response.jsonPath().get("id"), expectedId);
        softAssert.assertEquals(response.jsonPath().getString("mimeType"), expectedMimeType);
        softAssert.assertEquals(response.jsonPath().getString("detail"), expectedErrorMessage);

        softAssert.assertAll();
    }

    @DataProvider(name = "imageNames")
    public Object[][] imageNames() {
        return new Object[][]{
                {"d3413b9885a34ef8b52e461c3cbb0ef1", 200, 2, "d3413b9885a34ef8b52e461c3cbb0ef1", null},
                {"f", 404, null, null, "Resource was not found."}
        };
    }

    @Test(dataProvider = "imageNames")
    public void testGetImageByName(String name, int expectedStatusCode, Integer expectedId, String expectedBlobName, String expectedErrorMessage) {
        Response response = imageClient.getImageByName(name);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.statusCode(), expectedStatusCode);
        softAssert.assertEquals(response.jsonPath().get("id"), expectedId);
        softAssert.assertEquals(response.jsonPath().getString("blobName"), expectedBlobName);
        softAssert.assertEquals(response.jsonPath().getString("detail"), expectedErrorMessage);

        softAssert.assertAll();
    }
}