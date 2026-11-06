package org.academy.api.clients;

import io.restassured.specification.RequestSpecification;
import org.academy.api.BaseClient;
import org.academy.models.team.PublishedTeamResponse;
import org.academy.models.team.TeamCategoryDto;
import org.academy.models.team.TeamMemberDto;
import org.academy.models.team.TeamMemberDtoPaginationResult;

import java.util.Arrays;
import java.util.List;

public class TeamClient extends BaseClient {

    private static final String TEAM_ENDPOINT = "/Team";
    private static final String TEAM_CATEGORIES_ENDPOINT = "/TeamCategories";
    private static final String TEAM_MEMBERS_ENDPOINT = "/TeamMembers";

    // Створює клієнт команди із заданою базовою адресою API.
    public TeamClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    // Отримує категорії з опублікованими учасниками команди (GET /Team/published).
    public List<PublishedTeamResponse> getPublishedTeam() {
        PublishedTeamResponse[] groups = prepareRequest()
                .get(TEAM_ENDPOINT + "/published")
                .then()
                .statusCode(200)
                .extract()
                .as(PublishedTeamResponse[].class);
        return Arrays.asList(groups);
    }

    // Отримує список категорій команди (GET /TeamCategories).
    public List<TeamCategoryDto> getTeamCategories(){
        TeamCategoryDto[] groups = prepareRequest()
                .get(TEAM_CATEGORIES_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(TeamCategoryDto[].class);
        return Arrays.asList(groups);
    }

    // Отримує учасників із фільтрами та пагінацією (GET /TeamMembers). Параметри null не передаються.
    public TeamMemberDtoPaginationResult getTeamMembers(
            Long categoryId, Integer translationStatusFilter,
            Integer offset, Integer limit, Integer status) {
        RequestSpecification request = prepareRequest();
        if (categoryId != null) {
            request.queryParam("CategoryId", categoryId);
        }
        if (translationStatusFilter != null) {
            request.queryParam("TranslationStatusFilter", translationStatusFilter);
        }
        if (offset != null) {
            request.queryParam("Offset", offset);
        }
        if (limit != null) {
            request.queryParam("Limit", limit);
        }
        if (status != null) {
            request.queryParam("Status", status);
        }
        return request
                .get(TEAM_MEMBERS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(TeamMemberDtoPaginationResult.class);
    }

    // Шукає учасників за повним ім’ям із пагінацією (GET /TeamMembers/search). Параметри null не передаються.
    public TeamMemberDtoPaginationResult searchTeamMembers(
            String fullName, Integer offset, Integer limit) {
        RequestSpecification request = prepareRequest();
        if (fullName != null) {
            request.queryParam("FullName", fullName);
        }
        if (offset != null) {
            request.queryParam("Offset", offset);
        }
        if (limit != null) {
            request.queryParam("Limit", limit);
        }
        return request
                .get(TEAM_MEMBERS_ENDPOINT + "/search")
                .then()
                .statusCode(200)
                .extract()
                .as(TeamMemberDtoPaginationResult.class);
    }

    // Отримує одного учасника команди за його ID (GET /TeamMembers/{id}).
    public TeamMemberDto getTeamMemberById(Long id) {
        return prepareRequest()
                .pathParam("id", id)
                .get(TEAM_MEMBERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(TeamMemberDto.class);
    }
}
