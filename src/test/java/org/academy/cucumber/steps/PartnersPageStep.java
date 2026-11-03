package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.PartnerCardComponent;
import org.academy.ui.pages.PartnersPage;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class PartnersPageStep {

    private final CucumberHook cucumberHook;
    private PartnersPage partnersPage;

    public PartnersPageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("the Partners page is open")
    public void openPartnersPage() {
        partnersPage = new PartnersPage(cucumberHook.getDriver())
                .open(cucumberHook.getTestValueProvider().getBaseUrl());
    }

    @Then("the intro, partner sections, quote and footer appear in that order")
    public void verifySectionsOrder() {
        List<WebElement> sections = partnersPage.getPartnersSectionElements();
        Assert.assertFalse(sections.isEmpty(), "At least one partner section must be displayed");
        Rectangle previous = partnersPage.getIntroSectionElement().getRect();
        for (int index = 0; index < sections.size(); index++) {
            Rectangle current = sections.get(index).getRect();
            assertBelow(previous, current, "Partner section " + (index + 1));
            previous = current;
        }
        Rectangle quote = partnersPage.getCtaSectionElement().getRect();
        assertBelow(previous, quote, "Quote section");
        assertBelow(quote, partnersPage.getFooter().getBounds(), "Footer");
    }

    @Then("every partner section contains profiles with loaded images and non-empty descriptions")
    public void verifyPartnerProfiles() {
        int sectionCount = partnersPage.getPartnersSectionElements().size();
        Assert.assertTrue(sectionCount > 0, "At least one partner section must be displayed");
        for (int index = 0; index < sectionCount; index++) {
            List<PartnerCardComponent> cards = partnersPage.getPartnerCards(index);
            Assert.assertFalse(cards.isEmpty(), "Section " + (index + 1) + " must contain profiles");
            for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
                PartnerCardComponent card = cards.get(cardIndex);
                String name = "Profile " + (cardIndex + 1) + " in section " + (index + 1);
                Assert.assertFalse(card.getDescriptionText().isBlank(), name + " must have a description");
                Assert.assertTrue(card.isLogoLoaded(), name + " must have a loaded image");
            }
        }
    }

    @Given("at least two partner sections are displayed")
    public void verifyMultipleSectionsExist() {
        Assert.assertTrue(partnersPage.getPartnersSectionElements().size() >= 2,
                "Precondition: at least two published partner sections must exist");
    }

    @Then("every partner section has a non-empty heading and description")
    public void verifySectionHeadingsAndDescriptions() {
        int sectionCount = partnersPage.getPartnersSectionElements().size();
        for (int index = 0; index < sectionCount; index++) {
            WebElement title = partnersPage.getPartnerSectionTitle(index);
            WebElement description = partnersPage.getPartnerSectionDescription(index);
            String name = "Section " + (index + 1);
            Assert.assertFalse(title.getText().isBlank(), name + " must have a heading");
            Assert.assertFalse(description.getText().isBlank(), name + " must have a description");
            assertBelow(title.getRect(), description.getRect(), name + " description");
        }
    }

    @Then("partner profiles are contained in their section below its description")
    public void verifyProfilesGrouping() {
        List<WebElement> sections = partnersPage.getPartnersSectionElements();
        for (int index = 0; index < sections.size(); index++) {
            Rectangle section = sections.get(index).getRect();
            Rectangle description = partnersPage.getPartnerSectionDescription(index).getRect();
            List<PartnerCardComponent> cards = partnersPage.getPartnerCards(index);
            Assert.assertFalse(cards.isEmpty(), "Section " + (index + 1) + " must contain profiles");
            for (PartnerCardComponent card : cards) {
                Rectangle bounds = card.getBounds();
                assertInside(section, bounds, "Profile in section " + (index + 1));
                assertBelow(description, bounds, "Profile in section " + (index + 1));
            }
        }
    }

    @Then("partner profiles run left to right within rows without overlapping")
    public void verifyProfilesLayout() {
        int horizontalPairs = 0;
        int sectionCount = partnersPage.getPartnersSectionElements().size();
        for (int index = 0; index < sectionCount; index++) {
            List<PartnerCardComponent> cards = partnersPage.getPartnerCards(index);
            if (cards.size() < 2) {
                continue;
            }
            List<Rectangle> bounds = cards.stream().map(PartnerCardComponent::getBounds).toList();
            Rectangle previous = bounds.getFirst();
            int rowTop = previous.getY();
            int rowBottom = rowTop + previous.getHeight();
            for (int cardIndex = 1; cardIndex < bounds.size(); cardIndex++) {
                Rectangle current = bounds.get(cardIndex);
                String name = "Profile " + (cardIndex + 1) + " in section " + (index + 1);
                if (current.getY() < rowBottom) {
                    Assert.assertTrue(current.getY() >= rowTop, name + " must not move above its row");
                    Assert.assertTrue(current.getX() >= previous.getX() + previous.getWidth(),
                            name + " must follow the previous profile without overlapping");
                    horizontalPairs++;
                    rowBottom = Math.max(rowBottom, current.getY() + current.getHeight());
                } else {
                    rowTop = current.getY();
                    rowBottom = rowTop + current.getHeight();
                }
                previous = current;
            }
        }
        Assert.assertTrue(horizontalPairs > 0, "At least two partner profiles must be displayed side by side");
    }

    @When("the visitor scrolls to the Partners quote section")
    public void scrollToQuoteSection() {
        partnersPage.scrollToQuoteSection();
    }

    @Then("the quote section displays the Become a Partner button")
    public void verifyPartnerButton() {
        assertQuoteButton(partnersPage.getPartnerCtaButton(), "Стати партнером", "/support-us");
    }

    @Then("the quote section displays the Donate button")
    public void verifyDonateButton() {
        assertQuoteButton(partnersPage.getDonateCtaButton(), "Підтримати", "/donate");
    }

    @Then("the Partners intro displays a loaded image")
    public void verifyIntroImage() {
        partnersPage.waitForIntroImageLoadToFinish();
        Assert.assertTrue(partnersPage.isIntroImageLoaded(), "Intro image must be displayed and loaded");
    }

    @Then("the Partners intro contains a non-empty title and description below the title")
    public void verifyIntroText() {
        WebElement title = partnersPage.getIntroTitleElement();
        WebElement description = partnersPage.getIntroDescriptionElement();
        Assert.assertFalse(title.getText().isBlank(), "Intro title must not be blank");
        Assert.assertFalse(description.getText().isBlank(), "Intro description must not be blank");
        Rectangle intro = partnersPage.getIntroSectionElement().getRect();
        assertInside(intro, title.getRect(), "Intro title");
        assertInside(intro, description.getRect(), "Intro description");
        assertBelow(title.getRect(), description.getRect(), "Intro description");
    }

    private void assertQuoteButton(WebElement button, String expectedText, String expectedPath) {
        Assert.assertTrue(button.isDisplayed(), expectedText + " button must be visible");
        Assert.assertEquals(button.getText().trim(), expectedText, "Quote button text");
        Assert.assertEquals(button.getDomAttribute("href"), expectedPath, "Quote button destination");
    }

    private void assertBelow(Rectangle upper, Rectangle lower, String name) {
        Assert.assertTrue(lower.getY() >= upper.getY() + upper.getHeight(),
                name + " must be below the preceding element; upper bottom="
                        + (upper.getY() + upper.getHeight()) + ", lower top=" + lower.getY());
    }

    private void assertInside(Rectangle section, Rectangle element, String name) {
        Assert.assertTrue(element.getX() >= section.getX()
                && element.getY() >= section.getY()
                && element.getX() + element.getWidth() <= section.getX() + section.getWidth()
                && element.getY() + element.getHeight() <= section.getY() + section.getHeight(),
                name + " must be inside its section; section=" + section.getPoint() + " " + section.getDimension()
                        + ", element=" + element.getPoint() + " " + element.getDimension());
    }
}
