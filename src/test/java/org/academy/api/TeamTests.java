package org.academy.api;

import org.academy.api.clients.TeamClient;
import org.academy.models.team.PublishedTeamResponse;
import org.academy.models.team.PublishedTeamMembersDto;
import org.academy.models.team.TeamCategoryDto;
import org.academy.models.team.TeamMemberDto;
import org.academy.models.team.TeamMemberDtoPaginationResult;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TeamTests extends BaseAPITestRunner {

    private static final int PAGE_LIMIT = 10;

    private TeamClient teamClient;

    @BeforeClass
    public void beforeClass() {
        teamClient = new TeamClient(testEnvProvider.getBaseApiUrl());
        teamClient.setToken(getAccessToken());
    }

    // Перевіряє структуру категорій з опублікованими учасниками.
    @Test
    public void testGetPublishedTeam() {
        List<PublishedTeamResponse> categories = teamClient.getPublishedTeam();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(categories, "Published team response should not be null");
        if (categories != null) {
            for (PublishedTeamResponse category : categories) {
                softAssert.assertNotNull(category, "Published category should not be null");
                if (category == null) {
                    continue;
                }
                assertPositiveId(category.getId(), "Published category id", softAssert);
                // За контрактом teamMembers може бути null.
                if (category.getTeamMembers() != null) {
                    for (PublishedTeamMembersDto member : category.getTeamMembers()) {
                        softAssert.assertNotNull(member, "Published member should not be null");
                        if (member != null) {
                            assertPositiveId(member.getId(), "Published member id", softAssert);
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    // Перевіряє ідентифікатори категорій та кількість їхніх учасників.
    @Test
    public void testGetTeamCategories() {
        List<TeamCategoryDto> categories = teamClient.getTeamCategories();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(categories, "Categories response should not be null");
        if (categories != null) {
            for (TeamCategoryDto category : categories) {
                softAssert.assertNotNull(category, "Category should not be null");
                if (category == null) {
                    continue;
                }
                assertPositiveId(category.getId(), "Category id", softAssert);
                softAssert.assertTrue(category.getTeamMembersCount() != null
                                && category.getTeamMembersCount() >= 0,
                        "Team members count should be present and non-negative");
            }
        }
        softAssert.assertAll();
    }

    // Перевіряє сторінку учасників та фільтри категорії і статусу.
    @Test
    public void testGetTeamMembers() {
        SoftAssert softAssert = new SoftAssert();
        TeamMemberDtoPaginationResult page =
                teamClient.getTeamMembers(null, null, 0, PAGE_LIMIT, null);
        assertPage(page, PAGE_LIMIT, softAssert);
        if (page != null && page.getItems() != null && !page.getItems().isEmpty()) {
            TeamMemberDto sample = page.getItems().get(0);
            if (sample != null && sample.getCategoryId() != null && sample.getStatus() != null) {
                TeamMemberDtoPaginationResult filtered = teamClient.getTeamMembers(
                        sample.getCategoryId(), null, 0, PAGE_LIMIT, sample.getStatus());
                assertPage(filtered, PAGE_LIMIT, softAssert);
                if (filtered != null && filtered.getItems() != null) {
                    softAssert.assertFalse(filtered.getItems().isEmpty(),
                            "Filters should retain the sampled member on a stable dataset");
                    for (TeamMemberDto member : filtered.getItems()) {
                        if (member != null) {
                            softAssert.assertEquals(member.getCategoryId(), sample.getCategoryId(),
                                    "Category should match the requested filter");
                            softAssert.assertEquals(member.getStatus(), sample.getStatus(),
                                    "Status should match the requested filter");
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    // Шукає наявного учасника за повним ім'ям і перевіряє його наявність у результатах.
    @Test
    public void testSearchTeamMembers() {
        TeamMemberDto sample = getExistingMember();
        if (sample.getFullName() == null || sample.getFullName().isBlank()) {
            throw new SkipException("The sampled member has no full name for a search");
        }
        SoftAssert softAssert = new SoftAssert();
        TeamMemberDtoPaginationResult result =
                teamClient.searchTeamMembers(sample.getFullName(), 0, null);
        // Limit не задано: перевіряємо структуру без припущення про розмір сторінки.
        assertPage(result, null, softAssert);
        if (result != null && result.getItems() != null) {
            softAssert.assertFalse(result.getItems().isEmpty(),
                    "Searching an existing full name should return results");
            softAssert.assertTrue(result.getItems().stream()
                            .anyMatch(member -> member != null
                                    && sample.getFullName().equals(member.getFullName())),
                    "Search results should contain the requested full name");
        }
        softAssert.assertAll();
    }

    // Отримує наявного учасника за ID і порівнює його з даними зі списку.
    @Test
    public void testGetTeamMemberById() {
        TeamMemberDto sample = getExistingMember();
        TeamMemberDto member = teamClient.getTeamMemberById(sample.getId());
        SoftAssert softAssert = new SoftAssert();
        assertMember(member, softAssert);
        if (member != null) {
            softAssert.assertEquals(member.getId(), sample.getId(),
                    "Member id should match the requested id");
            softAssert.assertEquals(member.getFullName(), sample.getFullName(),
                    "Full name should match the list response");
            softAssert.assertEquals(member.getCategoryId(), sample.getCategoryId(),
                    "Category should match the list response");
            softAssert.assertEquals(member.getStatus(), sample.getStatus(),
                    "Status should match the list response");
        }
        softAssert.assertAll();
    }

    // Отримує тестові дані без створення чи зміни записів на сервері.
    private TeamMemberDto getExistingMember() {
        TeamMemberDtoPaginationResult page =
                teamClient.getTeamMembers(null, null, 0, 1, null);
        SoftAssert softAssert = new SoftAssert();
        assertPage(page, 1, softAssert);
        softAssert.assertAll();
        if (page.getItems().isEmpty()) {
            throw new SkipException("No existing team members are available for this test");
        }
        return page.getItems().get(0);
    }

    // Перевіряє структуру пагінації та кожного повернутого учасника.
    private void assertPage(TeamMemberDtoPaginationResult page, Integer limit,
                            SoftAssert softAssert) {
        softAssert.assertNotNull(page, "Pagination response should not be null");
        if (page == null) {
            return;
        }
        softAssert.assertTrue(page.getTotalItemsCount() != null && page.getTotalItemsCount() >= 0,
                "Total items count should be present and non-negative");
        softAssert.assertNotNull(page.getItems(), "Items should be present for a page response");
        if (page.getItems() == null) {
            return;
        }
        if (limit != null) {
            softAssert.assertTrue(page.getItems().size() <= limit,
                    "Page size should not exceed the requested limit");
        }
        if (page.getTotalItemsCount() != null) {
            softAssert.assertTrue(page.getTotalItemsCount() >= page.getItems().size(),
                    "Total items count should not be smaller than the page size");
        }
        for (TeamMemberDto member : page.getItems()) {
            assertMember(member, softAssert);
        }
    }

    // Перевіряє числові поля та допустимі значення статусу учасника.
    private void assertMember(TeamMemberDto member, SoftAssert softAssert) {
        softAssert.assertNotNull(member, "Member should not be null");
        if (member == null) {
            return;
        }
        assertPositiveId(member.getId(), "Member id", softAssert);
        assertPositiveId(member.getCategoryId(), "Member category id", softAssert);
        softAssert.assertNotNull(member.getPriority(), "Priority should be present");
        softAssert.assertTrue(Integer.valueOf(0).equals(member.getStatus())
                        || Integer.valueOf(1).equals(member.getStatus()),
                "Status should be 0 or 1 according to Swagger");
    }

    // Перевіряє ID без розпакування можливого null.
    private void assertPositiveId(Long id, String field, SoftAssert softAssert) {
        softAssert.assertTrue(id != null && id > 0, field + " should be present and positive");
    }
}
