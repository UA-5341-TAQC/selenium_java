package org.academy.api;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.academy.models.eventnews.EventNewsDto;
import org.academy.models.eventnews.EventNewsDtoPaginationResult;


public class EventNewsClient extends BaseClient{
    private static final String EVENT_NEWS_ENDPOINT = "/EventNews";
    private static final String PUBLISHED_ENDPOINT = EVENT_NEWS_ENDPOINT + "/published";
    private static final String PUBLISHED_BY_SLUG_ENDPOINT = PUBLISHED_ENDPOINT + "/{slug}";
    private static final String SEARCH_ENDPOINT = EVENT_NEWS_ENDPOINT + "/search";
    private static final String BY_ID_ENDPOINT = EVENT_NEWS_ENDPOINT + "/{id}";

    public EventNewsClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public EventNewsClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }


    public Response getPublishedEventNewsResponse() {
        return getPublishedEventNewsResponse(null, null, null);
    }

    public Response getPublishedEventNewsResponse(Integer offset, Integer limit) {
        return getPublishedEventNewsResponse(null, offset, limit);
    }

    public Response getPublishedEventNewsResponse(Long categoryId, Integer offset, Integer limit) {
        RequestSpecification req = prepareRequest();
        addQueryParam(req, "categoryId", categoryId);
        addQueryParam(req, "offset", offset);
        addQueryParam(req, "limit", limit);
        return req.get(PUBLISHED_ENDPOINT);
    }

    public EventNewsDtoPaginationResult getPublishedEventNews() {
        return getPublishedEventNews(null, null, null);
    }

    public EventNewsDtoPaginationResult getPublishedEventNews(Long categoryId, Integer offset, Integer limit) {
        return getPublishedEventNewsResponse(categoryId, offset, limit)
                .then()
                .statusCode(200)
                .extract()
                .as(EventNewsDtoPaginationResult.class);
    }

    public Response getPublishedEventNewsBySlugResponse(String slug) {
        return prepareRequest()
                .pathParam("slug", slug)
                .get(PUBLISHED_BY_SLUG_ENDPOINT);
    }

    public EventNewsDto getPublishedEventNewsBySlug(String slug) {
        return getPublishedEventNewsBySlugResponse(slug)
                .then()
                .statusCode(200)
                .extract()
                .as(EventNewsDto.class);
    }


    public Response getEventNewsResponse() {
        return getEventNewsResponse(null, null, null, null, null);
    }

    public Response getEventNewsResponse(Integer offset, Integer limit) {
        return getEventNewsResponse(offset, limit, null, null, null);
    }

    public Response getEventNewsResponse(Integer offset, Integer limit, Long categoryId,
                                         Integer status, Integer translationStatusFilter) {
        RequestSpecification req = prepareRequest();
        addQueryParam(req, "Offset", offset);
        addQueryParam(req, "Limit", limit);
        addQueryParam(req, "CategoryId", categoryId);
        addQueryParam(req, "Status", status);
        addQueryParam(req, "TranslationStatusFilter", translationStatusFilter);
        return req.get(EVENT_NEWS_ENDPOINT);
    }

    public EventNewsDtoPaginationResult getEventNews() {
        return getEventNews(null, null, null, null, null);
    }

    public EventNewsDtoPaginationResult getEventNews(Integer offset, Integer limit, Long categoryId,
                                                     Integer status, Integer translationStatusFilter) {
        return getEventNewsResponse(offset, limit, categoryId, status, translationStatusFilter)
                .then()
                .statusCode(200)
                .extract()
                .as(EventNewsDtoPaginationResult.class);
    }

    public Response searchEventNewsResponse(String searchQuery) {
        return searchEventNewsResponse(searchQuery, null, null);
    }

    public Response searchEventNewsResponse(String searchQuery, Integer offset, Integer limit) {
        RequestSpecification req = prepareRequest();
        addQueryParam(req, "SearchQuery", searchQuery);
        addQueryParam(req, "Offset", offset);
        addQueryParam(req, "Limit", limit);
        return req.get(SEARCH_ENDPOINT);
    }

    public EventNewsDtoPaginationResult searchEventNews(String searchQuery) {
        return searchEventNews(searchQuery, null, null);
    }

    public EventNewsDtoPaginationResult searchEventNews(String searchQuery, Integer offset, Integer limit) {
        return searchEventNewsResponse(searchQuery, offset, limit)
                .then()
                .statusCode(200)
                .extract()
                .as(EventNewsDtoPaginationResult.class);
    }

    public Response getEventNewsByIdResponse(long id) {
        return prepareRequest()
                .pathParam("id", id)
                .get(BY_ID_ENDPOINT);
    }

    public EventNewsDto getEventNewsById(long id) {
        return getEventNewsByIdResponse(id)
                .then()
                .statusCode(200)
                .extract()
                .as(EventNewsDto.class);
    }

    private static void addQueryParam(RequestSpecification req, String name, Object value) {
        if (value != null) {
            req.queryParam(name, value);
        }
    }
}
