package org.academy.api;

import org.academy.models.history.HistorySection;
import org.academy.models.history.HistoryLocalizationGroup;

import java.util.Arrays;
import java.util.List;

public class HistoryClient extends BaseClient {

    private static final String ENDPOINT = "/History";

    public HistoryClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public List<HistorySection> getHistory() {
        HistorySection[] sections = prepareRequest()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(HistorySection[].class);
        return Arrays.asList(sections);
    }

    public List<HistoryLocalizationGroup> getLocalizationsByEntityId(long entityId) {
        HistoryLocalizationGroup[] groups = prepareRequest()
                .get("/HistoryLocalizations/entityId/" + entityId)
                .then()
                .statusCode(200)
                .extract()
                .as(HistoryLocalizationGroup[].class);
        return Arrays.asList(groups);
    }

    public List<HistoryLocalizationGroup> getLocalizationsByLanguageId(long languageId) {
        HistoryLocalizationGroup[] groups = prepareRequest()
                .get("/HistoryLocalizations/languageId/" + languageId)
                .then()
                .statusCode(200)
                .extract()
                .as(HistoryLocalizationGroup[].class);
        return Arrays.asList(groups);
    }
}
