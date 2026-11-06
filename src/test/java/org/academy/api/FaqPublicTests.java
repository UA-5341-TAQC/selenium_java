package org.academy.api;

import io.restassured.response.Response;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.function.Function;

import static org.hamcrest.Matchers.empty;
import static org.testng.Assert.assertEquals;

public class FaqPublicTests extends BaseAPITestRunner {
    private FaqClient faqClient;

    @BeforeClass
    public void setUpFaqClient() {
        faqClient = new FaqClient(testEnvProvider.getBaseApiUrl());
    }

    @Test
    public void publishedQuestionsAreEmptyForUnknownSlug() {
        faqClient.getPublishedQuestions("qa-missing-faq-" + UUID.randomUUID())
                .then().statusCode(200).body("$", empty());
    }

    @DataProvider
    public Object[][] administrativeReads() {
        Function<FaqClient, Response> pages = FaqClient::getPages;
        Function<FaqClient, Response> questions = client -> client.getQuestions(0, 1);
        Function<FaqClient, Response> question = client -> client.getQuestion(1);
        Function<FaqClient, Response> search = client -> client.searchQuestions("faq", 0, 1);

        return new Object[][] {
                {"/Faq/pages", pages},
                {"/Faq", questions},
                {"/Faq/{id}", question},
                {"/Faq/search", search}
        };
    }

    @Test(dataProvider = "administrativeReads")
    public void administrativeReadsRequireAuthentication(String endpoint, Function<FaqClient, Response> read) {
        assertEquals(read.apply(faqClient).statusCode(), 401, "GET " + endpoint);
    }
}
