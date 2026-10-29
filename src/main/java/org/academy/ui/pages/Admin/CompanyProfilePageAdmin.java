package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.admin.CompanyProfileTabComponentAdmin;
import org.academy.ui.components.admin.CompanySocialMediaTabComponentAdmin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class CompanyProfilePageAdmin extends BaseAdminPage {
    @FindBy(css = "[class*='CompanyProfileContent_wrapper__']")
    private WebElement pageContent;

    @FindBy(css = "[class*='CompanyProfileLogoHeader_company-title__']")
    private WebElement companyTitle;

    @FindBy(css = "[class*='CompanyProfileLogoHeader_company-subtitle__']")
    private WebElement companySubtitle;

    @FindBy(xpath = "//button[contains(@class,'category-bar-button')][.//span[normalize-space()='Профіль компанії']]")
    private WebElement profileTabButton;

    @FindBy(xpath = "//button[contains(@class,'category-bar-button')][.//span[normalize-space()='Соціальні мережі']]")
    private WebElement socialMediaTabButton;

    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedTab;

    @FindBy(css = "[class*='CompanyProfileContent_tabs-wrapper__']")
    private WebElement tabsWrapper;

    @FindBy(css = "[data-testid='profile-page-toolbar'] .btn-primary")
    private WebElement primaryButton;

    @FindBy(css = "[data-testid='profile-page-toolbar'] .btn-secondary")
    private List<WebElement> cancelButtons;

    @FindBy(css = "[class*='CompanyProfileTab_profile-tab-container__']")
    private WebElement profileTabRoot;

    @FindBy(css = "[class*='CompanyProfileSocialMediaTab_social-media-tab-container__']")
    private WebElement socialMediaTabRoot;

    @FindBy(css = ".modal-container.confirmation-modal")
    private WebElement confirmationModalRoot;

    public CompanyProfilePageAdmin(WebDriver driver) {
        super(driver);
    }

    @Step("Check company profile page is displayed")
    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageContent)
        ).isDisplayed();
    }

    @Step("Get company title")
    public String getCompanyTitleText() {
        return companyTitle.getText().trim();
    }

    @Step("Get company subtitle")
    public String getCompanySubtitleText() {
        return companySubtitle.getText().trim();
    }

    @Step("Open company profile tab")
    public void openProfileTab() {
        wait.until(
                ExpectedConditions.elementToBeClickable(profileTabButton)
        ).click();

        wait.until(ExpectedConditions.visibilityOf(profileTabRoot));
    }

    @Step("Open social media tab")
    public void openSocialMediaTab() {
        wait.until(
                ExpectedConditions.elementToBeClickable(socialMediaTabButton)
        ).click();

        wait.until(ExpectedConditions.visibilityOf(socialMediaTabRoot));
    }

    @Step("Get selected tab title")
    public String getSelectedTabText() {
        return selectedTab.getText().trim();
    }

    @Step("Check profile tab is selected")
    public boolean isProfileTabSelected() {
        return profileTabButton.getDomAttribute("class")
                .contains("category-bar-button-selected");
    }

    @Step("Check social media tab is selected")
    public boolean isSocialMediaTabSelected() {
        return socialMediaTabButton.getDomAttribute("class")
                .contains("category-bar-button-selected");
    }

    @Step("Start editing company profile page")
    public void clickEditPage() {
        wait.until(driver ->
                primaryButton.getText().trim()
                        .equals("Редагувати сторінку"));

        wait.until(
                ExpectedConditions.elementToBeClickable(primaryButton)
        ).click();

        wait.until(driver -> isEditMode());
    }

    @Step("Check page is in edit mode")
    public boolean isEditMode() {
        return !cancelButtons.isEmpty()
                && cancelButtons.get(0).isDisplayed();
    }

    @Step("Check page is in view mode")
    public boolean isViewMode() {
        return !isEditMode()
                && primaryButton.getText().trim()
                .equals("Редагувати сторінку");
    }

    @Step("Get primary button text")
    public String getPrimaryButtonText() {
        return primaryButton.getText().trim();
    }

    @Step("Check publish button is enabled")
    public boolean isPublishEnabled() {
        return primaryButton.getText().trim()
                .equals("Опублікувати")
                && primaryButton.isEnabled();
    }

    @Step("Publish company profile changes")
    public void publishChanges() {
        wait.until(driver -> isPublishEnabled());

        wait.until(
                ExpectedConditions.elementToBeClickable(primaryButton)
        ).click();

        wait.until(driver -> isViewMode());
    }

    @Step("Request cancellation of editing")
    public void clickCancelEditing() {
        wait.until(driver -> isEditMode());

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cancelButtons.get(0))
        ).click();
    }

    @Step("Check tabs are disabled during editing")
    public boolean areTabsDisabledDuringEditing() {
        String className = tabsWrapper.getDomAttribute("class");

        return className != null
                && className.contains("tabs-wrapper-disabled__");
    }

    @Step("Get company profile tab component")
    public CompanyProfileTabComponentAdmin getProfileTab() {
        return new CompanyProfileTabComponentAdmin(
                wait.until(ExpectedConditions.visibilityOf(profileTabRoot))
        );
    }

    @Step("Get social media tab component")
    public CompanySocialMediaTabComponentAdmin getSocialMediaTab() {
        return new CompanySocialMediaTabComponentAdmin(
                wait.until(ExpectedConditions.visibilityOf(socialMediaTabRoot))
        );
    }
}
