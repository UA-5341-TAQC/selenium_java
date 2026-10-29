package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class CompanySocialMediaTabComponentAdmin extends BaseComponent {

    @FindBy(css = "[class*='CompanyProfileSocialMediaTab_social-media-tab-title__']")
    private WebElement sectionTitle;

    @FindBy(css = "button[aria-label='Show additional information']")
    private WebElement informationButton;

    @FindBy(id = "socialMediaSelect")
    private WebElement addContactButton;

    @FindBy(css = "[class*='CompanyProfileSocialMediaTab_social-media-contact__']")
    private List<WebElement> socialContacts;

    @FindBy(css = "[class*='CompanyProfileSocialMediaTab_social-media-contact-label__'] span")
    private List<WebElement> contactNames;

    @FindBy(css = "input[name^='socialContacts.'][name$='.url']")
    private List<WebElement> socialMediaInputs;

    @FindBy(css = "button[aria-label='Delete social contact']")
    private List<WebElement> deleteButtons;

    @FindBy(css = "output[id$='-character-count']")
    private List<WebElement> characterCounters;

    @FindBy(css = "[class*='InputErrorWithCharacterCounter_error-section__']")
    private List<WebElement> validationErrors;

    public CompanySocialMediaTabComponentAdmin(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check social media tab is displayed")
    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(rootElement)
        ).isDisplayed();
    }

    @Step("Get social media section title")
    public String getSectionTitleText() {
        return sectionTitle.getText().trim();
    }

    // Social media contacts

    @Step("Get social media contacts count")
    public int getContactsCount() {
        return socialContacts.size();
    }

    @Step("Get social media contact name at index {index}")
    public String getContactName(int index) {
        return contactNames.get(index).getText().trim();
    }

    @Step("Get all social media contact names")
    public List<String> getAllContactNames() {
        return contactNames.stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    @Step("Get social media URL at index {index}")
    public String getContactUrl(int index) {
        return socialMediaInputs.get(index).getDomProperty("value");
    }

    @Step("Get all social media URLs")
    public List<String> getAllContactUrls() {
        return socialMediaInputs.stream()
                .map(element -> element.getDomProperty("value"))
                .toList();
    }

    // Edit social media URLs

    @Step("Fill social media URL at index {index}")
    public void fillContactUrl(int index, String url) {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(
                        socialMediaInputs.get(index)
                )
        );

        input.clear();
        input.sendKeys(url);
    }

    @Step("Check social media URL is editable at index {index}")
    public boolean isContactUrlEditable(int index) {
        return socialMediaInputs.get(index).isEnabled();
    }

    @Step("Check all social media URLs are editable")
    public boolean areUrlsEditable() {
        return !socialMediaInputs.isEmpty()
                && socialMediaInputs.stream()
                .allMatch(WebElement::isEnabled);
    }

    @Step("Check all social media URLs are disabled")
    public boolean areUrlsDisabled() {
        return !socialMediaInputs.isEmpty()
                && socialMediaInputs.stream()
                .noneMatch(WebElement::isEnabled);
    }

    // Maximum length and character counters

    @Step("Get social media URL maximum length at index {index}")
    public int getUrlMaxLength(int index) {
        return Integer.parseInt(
                socialMediaInputs.get(index).getDomAttribute("maxlength")
        );
    }

    @Step("Get social media URL character counter at index {index}")
    public String getUrlCharacterCounter(int index) {
        WebElement input = socialMediaInputs.get(index);
        String inputId = input.getDomAttribute("id");

        return characterCounters.stream()
                .filter(counter -> (
                        inputId + "-character-count"
                ).equals(counter.getDomAttribute("id")))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Character counter not found for: " + inputId
                ))
                .getText()
                .trim();
    }

    @Step("Get all character counter texts")
    public List<String> getCharacterCounterTexts() {
        return characterCounters.stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    @Step("Get character counters count")
    public int getCharacterCountersCount() {
        return characterCounters.size();
    }

    // Validation

    @Step("Check social media URL has validation error at index {index}")
    public boolean hasUrlValidationError(int index) {
        return "true".equals(
                socialMediaInputs.get(index)
                        .getDomAttribute("aria-invalid")
        );
    }

    @Step("Get all visible URL validation error messages")
    public List<String> getValidationErrorMessages() {
        return validationErrors.stream()
                .filter(WebElement::isDisplayed)
                .map(element -> element.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();
    }

    @Step("Check any URL validation errors are displayed")
    public boolean hasAnyValidationErrors() {
        return !getValidationErrorMessages().isEmpty();
    }

    // Delete contacts

    @Step("Check delete contact button is enabled at index {index}")
    public boolean isDeleteButtonEnabled(int index) {
        return deleteButtons.get(index).isEnabled();
    }

    @Step("Delete social media contact at index {index}")
    public void deleteContact(int index) {
        int previousCount = getContactsCount();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButtons.get(index)
                )
        ).click();

        wait.until(driver ->
                getContactsCount() < previousCount
        );
    }

    // Add contact dropdown

    @Step("Open add social media contact dropdown")
    public void openAddContactDropdown() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addContactButton)
        ).click();

        wait.until(driver -> isAddContactDropdownExpanded());
    }

    @Step("Check add contact dropdown is expanded")
    public boolean isAddContactDropdownExpanded() {
        return "true".equals(
                addContactButton.getDomAttribute("aria-expanded")
        );
    }

    // Information tooltip

    @Step("Open social media information tooltip")
    public void openInformationTooltip() {
        wait.until(
                ExpectedConditions.elementToBeClickable(informationButton)
        ).click();
    }
}
