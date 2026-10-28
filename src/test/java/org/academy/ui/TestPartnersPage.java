package org.academy.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.components.PartnerCardComponent;
import org.academy.ui.pages.PartnersPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestPartnersPage extends BaseUiTestRunner {

    private static final String PARTNER_BUTTON_TEXT = "Стати партнером";
    private static final String DONATE_BUTTON_TEXT = "Підтримати";

    private static final String PARTNER_PATH = "/support-us";
    private static final String DONATE_PATH = "/donate";

    private static final String INTRO_SECTION = "Intro section";
    private static final String QUOTE_SECTION = "Quote section";
    private static final String SECTION_DESCRIPTION = "Section description";
    private static final String INTRO_HEADING = "Intro heading";
    private static final String INTRO_DESCRIPTION = "Intro description";

    private PartnersPage partnersPage;

    @BeforeMethod
    public void openPartnersPage() {
        partnersPage = new PartnersPage(driver).open(testEnvProvider.getBaseUrl());
    }

    @Test(description = "Verify the order of sections on the public Partners page")
    @Issue("23")
    @Story("Section Order on Partners Page")
    public void testSectionsOrder() {
        waitUntilLayoutReady();

        List<WebElement> partnerSections = partnersPage.getPartnersSectionElements();
        WebElement intro = partnersPage.getIntroSectionElement();
        WebElement quote = partnersPage.getCtaSectionElement();
        FooterComponent footer = partnersPage.getFooter();

        // Intro section -> partner sections
        Rectangle previousBounds = intro.getRect();
        String previousName = INTRO_SECTION;

        for (int index = 0; index < partnerSections.size(); index++) {
            Rectangle currentBounds = partnerSections.get(index).getRect();
            String currentName = "Partner section " + (index + 1);
            Rectangle upperBounds = previousBounds;
            String upperName = previousName;

            Allure.step("Verify " + currentName + " is below " + upperName,
                    () -> assertBelow(upperName, upperBounds, currentName, currentBounds));

            previousBounds = currentBounds;
            previousName = currentName;
        }

        // Last partner section -> quote section -> footer
        Rectangle lastPartnerBounds = previousBounds;
        String lastPartnerName = previousName;
        Rectangle quoteBounds = quote.getRect();

        Allure.step("Verify quote section is below the last partner section",
                () -> assertBelow(lastPartnerName, lastPartnerBounds, QUOTE_SECTION, quoteBounds));
        Allure.step("Verify footer is below the quote section",
                () -> assertBelow(QUOTE_SECTION, quoteBounds, "Footer", footer.getBounds()));
    }

    @Test(description = "Verify every partner profile has a loaded image and description within its section")
    @Issue("24")
    @Story("Partner Profiles")
    public void testPartnerProfilesContent() {
        int sectionCount = partnersPage.getPartnersSectionElements().size();

        for (int sectionIndex = 0; sectionIndex < sectionCount; sectionIndex++) {
            List<PartnerCardComponent> cards = partnersPage.getPartnerCards(sectionIndex);

            Assert.assertFalse(cards.isEmpty(), "Partner section " + (sectionIndex + 1) + " must contain profiles");

            for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
                PartnerCardComponent card = cards.get(cardIndex);
                String profileName = "Profile " + (cardIndex + 1) + " in section " + (sectionIndex + 1);

                Allure.step("Verify content of " + profileName, () -> {
                    Allure.step("Verify profile description is visible and non-empty", () ->
                            Assert.assertFalse(card.getDescriptionText().isBlank(), profileName + " must have a description"));
                    Allure.step("Verify profile logo is displayed and loaded", () ->
                            Assert.assertTrue(card.isLogoLoaded(), profileName + " must have a loaded image"));
                });
            }
        }
    }

    @Test(description = "Verify partner profiles are grouped into separate sections with headings and descriptions")
    @Issue("25")
    @Story("Partner Sections Grouping")
    public void testPartnerSectionsGrouping() {
        waitUntilLayoutReady();

        List<WebElement> sections = partnersPage.getPartnersSectionElements();

        Assert.assertTrue(sections.size() >= 2, "Precondition: at least two published partner sections must exist");

        for (int index = 0; index < sections.size(); index++) {
            int sectionIndex = index;

            Allure.step("Verify grouping of partner section " + (index + 1), () -> {
                WebElement title = partnersPage.getPartnerSectionTitle(sectionIndex);
                WebElement description = partnersPage.getPartnerSectionDescription(sectionIndex);

                Allure.step("Verify section heading is visible and non-empty", () ->
                        Assert.assertFalse(title.getText().isBlank(), "Section heading must not be blank"));
                Allure.step("Verify section description is visible and non-empty", () ->
                        Assert.assertFalse(description.getText().isBlank(), "Section description must not be blank"));

                Rectangle descriptionBounds = description.getRect();

                Allure.step("Verify section description is below the heading", () ->
                        assertBelow("Section heading", title.getRect(), SECTION_DESCRIPTION, descriptionBounds));

                List<PartnerCardComponent> cards = partnersPage.getPartnerCards(sectionIndex);

                Allure.step("Verify section contains partner profiles", () ->
                        Assert.assertFalse(cards.isEmpty(), "Each partner section must contain profiles"));

                Rectangle sectionBounds = sections.get(sectionIndex).getRect();

                for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
                    Rectangle cardBounds = cards.get(cardIndex).getBounds();
                    String profileName = "Profile " + (cardIndex + 1) + " in section " + (sectionIndex + 1);

                    Allure.step("Verify placement of " + profileName, () -> {
                        assertInside("Partner section " + (sectionIndex + 1), sectionBounds, profileName, cardBounds);
                        assertBelow(SECTION_DESCRIPTION, descriptionBounds, profileName, cardBounds);
                    });
                }
            });
        }
    }

    @Test(description = "Verify partner profiles run left-to-right within rows and wrap below the previous row")
    @Issue("26")
    @Story("Partner Profiles Layout")
    public void testPartnerProfilesHorizontalLayout() {
        waitUntilLayoutReady();

        int checkedSections = 0;
        int horizontalPairs = 0;
        int sectionCount = partnersPage.getPartnersSectionElements().size();

        for (int sectionIndex = 0; sectionIndex < sectionCount; sectionIndex++) {
            List<PartnerCardComponent> cards = partnersPage.getPartnerCards(sectionIndex);

            if (cards.size() >= 2) {
                String sectionName = "Partner section " + (sectionIndex + 1);
                List<Rectangle> bounds = cards.stream().map(PartnerCardComponent::getBounds).toList();

                horizontalPairs += Allure.step("Verify rows in " + sectionName,
                        () -> assertProfilesRows(sectionName, bounds));
                checkedSections++;
            }
        }

        Assert.assertTrue(checkedSections > 0, "Precondition: a section with at least two partners must exist");
        Assert.assertTrue(horizontalPairs > 0, "At least two partner profiles must be displayed side by side");
    }

    @Test(description = "Verify the quote section displays Become a Partner and Donate buttons")
    @Issue("30")
    @Story("Partners Quote Section Buttons")
    public void testQuoteSectionButtons() {
        partnersPage.scrollToQuoteSection();

        assertCtaButton(partnersPage.getPartnerCtaButton(), PARTNER_BUTTON_TEXT, PARTNER_PATH);
        assertCtaButton(partnersPage.getDonateCtaButton(), DONATE_BUTTON_TEXT, DONATE_PATH);
    }

    @Test(description = "Verify the Partners intro displays an image, heading and description")
    @Issue("31")
    @Story("Partners Intro Section")
    public void testIntroSectionContent() {
        waitUntilLayoutReady();

        WebElement title = partnersPage.getIntroTitleElement();
        WebElement description = partnersPage.getIntroDescriptionElement();

        Allure.step("Verify intro heading and description are visible and non-empty", () -> {
            Allure.step("Verify intro heading", () ->
                    Assert.assertFalse(title.getText().isBlank(), "Intro heading must not be blank"));
            Allure.step("Verify intro description", () ->
                    Assert.assertFalse(description.getText().isBlank(), "Intro description must not be blank"));
        });

        Allure.step("Verify intro heading and description are contained in the intro block", () -> {
            Rectangle introBounds = partnersPage.getIntroSectionElement().getRect();

            Allure.step("Verify heading is inside the intro block", () ->
                    assertInside(INTRO_SECTION, introBounds, INTRO_HEADING, title.getRect()));
            Allure.step("Verify description is inside the intro block", () ->
                    assertInside(INTRO_SECTION, introBounds, INTRO_DESCRIPTION, description.getRect()));
            Allure.step("Verify description is below the heading", () ->
                    assertBelow(INTRO_HEADING, title.getRect(), INTRO_DESCRIPTION, description.getRect()));
        });

        partnersPage.waitForIntroImageLoadToFinish();

        Allure.step("Verify intro background image is displayed and loaded", () ->
                Assert.assertTrue(partnersPage.isIntroImageLoaded(), "Intro background image must load successfully"));
    }

    private void waitUntilLayoutReady() {
        List<WebElement> sections = new ArrayList<>();
        sections.add(partnersPage.getIntroSectionElement());
        sections.addAll(partnersPage.getPartnersSectionElements());
        sections.add(partnersPage.getCtaSectionElement());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        sections.forEach(section -> js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", section));

        FooterComponent footer = partnersPage.getFooter().scrollIntoView();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(new ExpectedCondition<Boolean>() {
            private List<Rectangle> previousBounds;

            @Override
            public Boolean apply(WebDriver webDriver) {
                if (!Boolean.TRUE.equals(js.executeScript("return document.fonts.status === 'loaded';"))) {
                    previousBounds = null;
                    return false;
                }

                List<Rectangle> currentBounds = new ArrayList<>(sections.stream().map(WebElement::getRect).toList());
                currentBounds.add(footer.getBounds());
                boolean stable = currentBounds.equals(previousBounds);
                previousBounds = currentBounds;
                return stable;
            }

            @Override
            public String toString() {
                return "Partners page section bounds to stabilize";
            }
        });
    }

    private int assertProfilesRows(String sectionName, List<Rectangle> bounds) {
        Rectangle previous = bounds.getFirst();
        int rowTop = previous.getY();
        int rowBottom = rowTop + previous.getHeight();
        int horizontalPairs = 0;

        for (int index = 1; index < bounds.size(); index++) {
            Rectangle current = bounds.get(index);
            String profileName = "Profile " + (index + 1) + " in " + sectionName;
            String diagnostic = "; previous=" + formatBounds(previous) + ", current=" + formatBounds(current);

            if (current.getY() < rowBottom) {
                Assert.assertTrue(current.getY() >= rowTop,
                        profileName + " must not move above its row" + diagnostic);
                Assert.assertTrue(current.getX() >= previous.getX() + previous.getWidth(),
                        profileName + " must follow the previous profile left-to-right without overlapping" + diagnostic);

                horizontalPairs++;
                rowBottom = Math.max(rowBottom, current.getY() + current.getHeight());
            } else {
                rowTop = current.getY();
                rowBottom = rowTop + current.getHeight();
            }

            previous = current;
        }

        return horizontalPairs;
    }

    private void assertCtaButton(WebElement button, String expectedText, String expectedPath) {
        Allure.step("Verify quote button: " + expectedText, () -> {
            Allure.step("Verify button is visible and enabled", () -> {
                Assert.assertTrue(button.isDisplayed(), "CTA button must be visible");
                Assert.assertTrue(button.isEnabled(), "CTA button must be enabled");
            });
            Allure.step("Verify button label", () ->
                    Assert.assertEquals(button.getText(), expectedText, "CTA label must match the expected text"));
            Allure.step("Verify button destination", () ->
                    Assert.assertEquals(button.getDomAttribute("href"), expectedPath, "CTA destination must match its purpose"));
        });
    }

    private void assertInside(String sectionName, Rectangle section, String elementName, Rectangle card) {
        Assert.assertTrue(card.getX() >= section.getX()
                        && card.getY() >= section.getY()
                        && card.getX() + card.getWidth() <= section.getX() + section.getWidth()
                        && card.getY() + card.getHeight() <= section.getY() + section.getHeight(),
                elementName + " must be contained within " + sectionName
                        + "; section bounds=" + formatBounds(section) + ", element bounds=" + formatBounds(card));
    }

    private String formatBounds(Rectangle bounds) {
        return String.format("[x=%d, y=%d, width=%d, height=%d]",
                bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    private void assertBelow(String upperName, Rectangle upperBounds,
            String lowerName, Rectangle lowerBounds) {
        int upperBottom = upperBounds.getY() + upperBounds.getHeight();
        String message = lowerName + " must be below " + upperName
                + ": upper bottom=" + upperBottom
                + ", lower top=" + lowerBounds.getY();

        Assert.assertTrue(lowerBounds.getY() >= upperBottom, message);
    }
}
