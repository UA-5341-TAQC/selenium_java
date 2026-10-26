package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.ContactFormPopUpComponent;
import org.academy.ui.components.ContactSectionComponent;
import org.academy.ui.components.FaqSectionComponent;
import org.academy.ui.components.hippotherapy.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HippotherapyPage extends BasePublicPage {

    @FindBy(css = "section[class*='HippotherapyIntro_root'], [class*='HippotherapyIntro_root']")
    private WebElement introElement;

    @FindBy(css = "section[class*='TextCard_text-card-section']")
    private List<WebElement> textCardElements;

    @FindBy(css = "section[class*='QuoteSection_root'], [class*='QuoteSection_root']")
    private List<WebElement> quoteSectionElements;

    @FindBy(css = "h1[data-testid='slogan-section'], [class*='SloganSection_slogan']")
    private WebElement sloganElement;

    @FindBy(css = "section[class*='HippoventionCenter_hippovention-center'], [class*='HippoventionCenter_hippovention-center']")
    private WebElement hippoventionCenterElement;

    @FindBy(css = "section[class*='SwipedGallery_root'], [class*='SwipedGallery_root']")
    private List<WebElement> swipedGalleryElements;

    @FindBy(css = "section[class*='ResearchSection_root'], [class*='ResearchSection_root']")
    private WebElement researchSectionElement;

    @FindBy(css = "section[class*='EthicalPrinciples_ethical-principles'], [class*='EthicalPrinciples_ethical-principles']")
    private WebElement ethicalPrinciplesElement;

    @FindBy(css = "div[class*='FaqSection_faq-section'], [class*='FaqSection_faq-section']")
    private WebElement faqSectionElement;

    @FindBy(css = "div[class*='ContactSection_contact-us-block'], [class*='ContactSection_contact-us-block']")
    private WebElement contactSectionElement;

    public HippotherapyPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get HippotherapyIntro component")
    public HippotherapyIntroComponent getIntro() {
        return new HippotherapyIntroComponent(waitUntilVisible(introElement));
    }

    @Step("Get list of TextCard components")
    public List<TextCardComponent> getTextCards() {
        return textCardElements.stream()
                .map(TextCardComponent::new)
                .toList();
    }

    @Step("Get TextCard component by index: {index}")
    public TextCardComponent getTextCard(int index) {
        if (index < 0 || index >= textCardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid TextCard index: " + index + ", total cards: " + textCardElements.size());
        }
        return new TextCardComponent(textCardElements.get(index));
    }

    @Step("Get list of QuoteSection components")
    public List<QuoteSectionComponent> getQuoteSections() {
        return quoteSectionElements.stream()
                .map(QuoteSectionComponent::new)
                .toList();
    }

    @Step("Get QuoteSection component by index: {index}")
    public QuoteSectionComponent getQuoteSection(int index) {
        if (index < 0 || index >= quoteSectionElements.size()) {
            throw new IndexOutOfBoundsException("Invalid quote section index: " + index + ", total quotes: " + quoteSectionElements.size());
        }
        return new QuoteSectionComponent(quoteSectionElements.get(index));
    }

    @Step("Get SloganSection component")
    public SloganSectionComponent getSloganSection() {
        return new SloganSectionComponent(waitUntilVisible(sloganElement));
    }

    @Step("Get HippoventionCenter component")
    public HippoventionCenterComponent getHippoventionCenterSection() {
        return new HippoventionCenterComponent(waitUntilVisible(hippoventionCenterElement));
    }

    @Step("Get list of SwipedGallery components")
    public List<SwipedGalleryComponent> getSwipedGalleries() {
        return swipedGalleryElements.stream()
                .map(SwipedGalleryComponent::new)
                .toList();
    }

    @Step("Get SwipedGallery component by index: {index}")
    public SwipedGalleryComponent getSwipedGallery(int index) {
        if (index < 0 || index >= swipedGalleryElements.size()) {
            throw new IndexOutOfBoundsException("Invalid swiped gallery index: " + index + ", total galleries: " + swipedGalleryElements.size());
        }
        return new SwipedGalleryComponent(swipedGalleryElements.get(index));
    }

    @Step("Get ResearchSection component")
    public ResearchSectionComponent getResearchSection() {
        return new ResearchSectionComponent(waitUntilVisible(researchSectionElement));
    }

    @Step("Get EthicalPrinciples component")
    public EthicalPrinciplesComponent getEthicalPrinciplesSection() {
        return new EthicalPrinciplesComponent(waitUntilVisible(ethicalPrinciplesElement));
    }

    @Step("Get FaqSection component")
    public FaqSectionComponent getFaqSection() {
        return new FaqSectionComponent(waitUntilVisible(faqSectionElement));
    }

    @Step("Get ContactSection component")
    public ContactSectionComponent getContactSection() {
        return new ContactSectionComponent(waitUntilVisible(contactSectionElement));
    }

    @Step("Open Contact Form popup component via Contact section button")
    public ContactFormPopUpComponent openContactFormModal() {
        return getContactSection().clickContactButton();
    }

    @Step("Get ContactFormPopUpComponent")
    public ContactFormPopUpComponent getContactFormPopUpComponent() {
        return new ContactFormPopUpComponent(driver);
    }

    @Step("Check if ContactFormPopUpComponent is displayed")
    public boolean isContactFormPopUpDisplayed() {
        try {
            return getContactFormPopUpComponent().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
