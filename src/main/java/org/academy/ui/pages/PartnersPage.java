package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PartnersPage extends BasePublicPage {

    private static final String PAGE_PATH = "/partners-page";
    private static final String ENGLISH_PAGE_PATH = "/en/partners-page";

    @FindBy(css = "[class*='IntroSection_intro-block']")
    private WebElement introSection;

    @FindBy(css = "[class*='IntroSection_title']")
    private WebElement introTitle;

    @FindBy(css = "[class*='IntroSection_subtitle']")
    private WebElement introDescription;

    @FindBy(css = "[class*='IntroSection_bg-img']")
    private WebElement introBackgroundImage;

    @FindBy(css = "section[class*='PartnersSection_partners-content-section']")
    private List<WebElement> partnersSections;

    @FindBy(css = "[class*='PartnersSection_section-title']")
    private List<WebElement> partnersSectionTitles;

    @FindBy(css = "[class*='PartnersSection_section-description']")
    private List<WebElement> partnersSectionDescriptions;

    @FindBy(css = "section[class*='CtaSection_root']")
    private WebElement ctaSection;

    @FindBy(css = "[class*='BackgroundMedia_media']")
    private WebElement ctaBackgroundVideo;

    @FindBy(css = "[class*='CtaSection_title']")
    private WebElement ctaTitle;

    @FindBy(css = "[class*='CtaSection_description']")
    private WebElement ctaDescription;

    @FindBy(css = "[class*='CtaSection_actions'] a[href*='/support-us']")
    private WebElement partnerCtaButton;

    @FindBy(css = "[class*='CtaSection_actions'] a[href*='/donate']")
    private WebElement donateCtaButton;

    public PartnersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Open the Ukrainian Partners page")
    public PartnersPage open(String baseUrl) {
        driver.get(normalizeBaseUrl(baseUrl) + PAGE_PATH);
        return waitUntilLoaded();
    }

    @Step("Open the English Partners page")
    public PartnersPage openEnglish(String baseUrl) {
        driver.get(normalizeBaseUrl(baseUrl) + ENGLISH_PAGE_PATH);
        return waitUntilLoaded();
    }

    @Step("Wait for the Partners page to load")
    public PartnersPage waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOf(introSection));
        return this;
    }

    @Step("Get the Partners page intro section element")
    public WebElement getIntroSectionElement() {
        return wait.until(ExpectedConditions.visibilityOf(introSection));
    }

    @Step("Get the Partners page intro title element")
    public WebElement getIntroTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(introTitle));
    }

    @Step("Get the Partners page intro description element")
    public WebElement getIntroDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOf(introDescription));
    }

    @Step("Get the Partners page intro background image element")
    public WebElement getIntroBackgroundImageElement() {
        return wait.until(ExpectedConditions.visibilityOf(introBackgroundImage));
    }

    @Step("Get partner sections")
    public List<WebElement> getPartnersSectionElements() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(partnersSections));
    }

    @Step("Get partner section title elements")
    public List<WebElement> getPartnersSectionTitleElements() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(partnersSectionTitles));
    }

    @Step("Get partner section description elements")
    public List<WebElement> getPartnersSectionDescriptionElements() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(partnersSectionDescriptions));
    }

    @Step("Get partner items in section at index: {sectionIndex}")
    public List<WebElement> getPartnerItems(int sectionIndex) {
        return getPartnersSectionAt(sectionIndex).findElements(By.cssSelector("[class*='PartnersSection_partner-item']"));
    }

    @Step("Get partner logos in section at index: {sectionIndex}")
    public List<WebElement> getPartnerLogos(int sectionIndex) {
        return getPartnersSectionAt(sectionIndex).findElements(By.cssSelector("[class*='PartnersSection_partner-logo']"));
    }

    @Step("Get partner names in section at index: {sectionIndex}")
    public List<WebElement> getPartnerNames(int sectionIndex) {
        return getPartnersSectionAt(sectionIndex).findElements(By.cssSelector("[class*='PartnersSection_partner-name']"));
    }

    @Step("Get the Partners page CTA section element")
    public WebElement getCtaSectionElement() {
        return wait.until(ExpectedConditions.visibilityOf(ctaSection));
    }

    @Step("Get the Partners page CTA background video element")
    public WebElement getCtaBackgroundVideoElement() {
        return wait.until(ExpectedConditions.visibilityOf(ctaBackgroundVideo));
    }

    @Step("Get the Partners page CTA title element")
    public WebElement getCtaTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(ctaTitle));
    }

    @Step("Get the Partners page CTA description element")
    public WebElement getCtaDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOf(ctaDescription));
    }

    @Step("Get the Become a Partner CTA button")
    public WebElement getPartnerCtaButton() {
        return wait.until(ExpectedConditions.visibilityOf(partnerCtaButton));
    }

    @Step("Get the Donate CTA button")
    public WebElement getDonateCtaButton() {
        return wait.until(ExpectedConditions.visibilityOf(donateCtaButton));
    }

    private WebElement getPartnersSectionAt(int sectionIndex) {
        List<WebElement> sections = getPartnersSectionElements();
        if (sectionIndex < 0 || sectionIndex >= sections.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid partner section index: " + sectionIndex + ", total sections: " + sections.size()
            );
        }
        return sections.get(sectionIndex);
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL must not be null or blank");
        }
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}
