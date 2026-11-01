package org.academy.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.academy.ui.modals.admin.history.HistoryTranslateModal;
import org.academy.ui.pages.Admin.AdminHistoryPage;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Feature("History Page Admin")
public class TestHistoryPageAdmin extends UITestRunnerWithAdmin {

    private AdminHistoryPage adminHistoryPage;

    @BeforeMethod
    public void openAdminHistoryPage() {
        adminHistoryPage = adminPanelPage
                .getNavigation()
                .clickHistoryLink();
    }

    @Issue("620")
    @Description("Verify 'Редагувати переклад' modal opens with the correct History structure and existing English translation")
    @Owner("Svitlana Kovalova")
    @Test
    public void testTranslationModalContent() {

        int expectedSectionsCount = adminHistoryPage.getSectionsCount();

        HistoryTranslateModal translateModal = adminHistoryPage.openTranslationModal();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(translateModal.getTranslationControls().isDisplayed(), "Translation modal should be displayed");
        softAssert.assertTrue(translateModal.getSectionsList().isDisplayed(), "History sections should be displayed");
        softAssert.assertEquals(translateModal.getSectionRowsCount(), expectedSectionsCount, "History sections count should match");
        softAssert.assertEquals(translateModal.getSectionTitles().size(), expectedSectionsCount, "Each section should have a translation title field");
        softAssert.assertEquals(translateModal.getCurrentLanguage(), "Англійська", "English language should be selected");
        softAssert.assertEquals(
                translateModal.getSectionTitles().get(1),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed",
                "Existing English translation should be displayed"
        );

        translateModal.close();
        softAssert.assertAll();
    }

    @Issue("622")
    @Description("Verify language dropdown in 'Редагувати переклад' modal window")
    @Owner("Svitlana Kovalova")
    @Test
    public void testTranslationLanguageDropdown() {

        HistoryTranslateModal translateModal = adminHistoryPage.openTranslationModal();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(translateModal.getTranslationControls().isDisplayed(), "Translation controls should be displayed");
        softAssert.assertTrue(translateModal.getSectionsList().isDisplayed(), "History sections should be displayed");
        softAssert.assertEquals(translateModal.getCurrentLanguage(), "Англійська", "English should be selected by default");

        translateModal.openLanguageList();

        softAssert.assertEquals(translateModal.getLanguageOptionTexts(), List.of("Англійська"), "English should be the only available language option");
        softAssert.assertEquals(translateModal.getSelectedLanguageOption(), "Англійська", "English should be the selected language option");

        translateModal.close();
        softAssert.assertAll();
    }

    @Issue("739")
    @Description("Verify clicking the Down arrow moves the section one position lower")
    @Owner("Svitlana Kovalova")
    @Test
    public void testMoveSectionDown() {

        List<String> originalOrder = adminHistoryPage.getSectionTitles();

        Assert.assertTrue(originalOrder.size() >= 3, "At least 3 sections are required");

        SoftAssert softAssert = new SoftAssert();

        String firstSection = originalOrder.get(0);

        List<String> expectedFirstOrder = new ArrayList<>(originalOrder);
        Collections.swap(expectedFirstOrder, 0, 1);

        adminHistoryPage.clickMoveDownSectionButton(firstSection);
        adminHistoryPage.waitForSectionOrder(expectedFirstOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), expectedFirstOrder, "First section should move one position down");

        int middleIndex = (expectedFirstOrder.size() - 1) / 2;
        String middleSection = expectedFirstOrder.get(middleIndex);

        List<String> expectedMiddleOrder = new ArrayList<>(expectedFirstOrder);
        Collections.swap(expectedMiddleOrder, middleIndex, middleIndex + 1);

        adminHistoryPage.clickMoveDownSectionButton(middleSection);
        adminHistoryPage.waitForSectionOrder(expectedMiddleOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), expectedMiddleOrder, "Middle section should move one position down");

        adminHistoryPage.restoreSectionOrder(originalOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), originalOrder, "Original section order should be restored");
        softAssert.assertAll();
    }

    @Issue("740")
    @Description("Verify clicking the Up arrow moves the section one position higher")
    @Owner("Svitlana Kovalova")
    @Test
    public void testMoveSectionUp() {

        List<String> originalOrder = adminHistoryPage.getSectionTitles();

        Assert.assertTrue(originalOrder.size() >= 3, "At least 3 sections are required");

        SoftAssert softAssert = new SoftAssert();

        int middleIndex = originalOrder.size() / 2;
        String middleSection = originalOrder.get(middleIndex);

        List<String> expectedMiddleOrder = new ArrayList<>(originalOrder);
        Collections.swap(expectedMiddleOrder, middleIndex, middleIndex - 1);

        adminHistoryPage.clickMoveUpSectionButton(middleSection);
        adminHistoryPage.waitForSectionOrder(expectedMiddleOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), expectedMiddleOrder, "Middle section should move one position up");

        int lastIndex = expectedMiddleOrder.size() - 1;
        String lastSection = expectedMiddleOrder.get(lastIndex);

        List<String> expectedLastOrder = new ArrayList<>(expectedMiddleOrder);
        Collections.swap(expectedLastOrder, lastIndex, lastIndex - 1);

        adminHistoryPage.clickMoveUpSectionButton(lastSection);
        adminHistoryPage.waitForSectionOrder(expectedLastOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), expectedLastOrder, "Last section should move one position up");

        adminHistoryPage.restoreSectionOrder(originalOrder);

        softAssert.assertEquals(adminHistoryPage.getSectionTitles(), originalOrder, "Original section order should be restored");
        softAssert.assertAll();
    }
}
