package org.academy.ui.modals.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.pages.Admin.AdminProgramsPage;
import org.academy.ui.types.ProgramField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseProgramModal<T extends BaseProgramModal<T>> extends BaseModal {

    @FindBy(css = "#name")
    private WebElement name;

    @FindBy(css = "#description")
    private WebElement description;

    @FindBy(css = "#location")
    private WebElement location;

    @FindBy(css = "#participantsCount")
    private WebElement participantsCount;

    @FindBy(css = "#meetingCount")
    private WebElement meetingCount;

    @FindBy(css = ".input-error")
    private List<WebElement> validationErrors;

    @FindBy(css = "input[aria-invalid='true'], textarea[aria-invalid='true']")
    private List<WebElement> invalidFields;

    @FindBy(css = "output[for]")
    private List<WebElement> characterCounters;

    protected BaseProgramModal(WebElement rootElement) {
        super(rootElement);
    }

    protected abstract T self();

    @Step("Read program validation errors")
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        for (WebElement error : validationErrors) {
            if (error.isDisplayed()) {
                errors.add(error.getText());
            }
        }
        return errors;
    }

    @Step("Read invalid program field names")
    public List<String> getInvalidFieldNames() {
        List<String> names = new ArrayList<>();
        for (WebElement field : invalidFields) {
            names.add(field.getDomAttribute(HtmlAttribute.NAME.getValue()));
        }
        return names;
    }

    @Step("Read character counter for program field {field}")
    public String getCharacterCounter(ProgramField field) {
        Objects.requireNonNull(field, "Program field must not be null");
        for (WebElement counter : characterCounters) {
            if (field.getId().equals(counter.getDomAttribute(HtmlAttribute.FOR.getValue()))) {
                return getText(counter);
            }
        }
        throw new IllegalStateException("No character counter for field: " + field);
    }

    @Step("Read program name")
    public String getName() {
        return getValue(name);
    }

    @Step("Set program name")
    public T setName(String value) {
        fillInput(name, value);
        return self();
    }

    @Step("Read program description")
    public String getDescription() {
        return getValue(description);
    }

    @Step("Set program description")
    public T setDescription(String value) {
        fillInput(description, value);
        return self();
    }

    @Step("Read program location")
    public String getLocation() {
        return getValue(location);
    }

    @Step("Set program location")
    public T setLocation(String value) {
        fillInput(location, value);
        return self();
    }

    @Step("Read program participant count")
    public String getParticipantsCount() {
        return getValue(participantsCount);
    }

    @Step("Set program participant count")
    public T setParticipantsCount(String value) {
        fillInput(participantsCount, value);
        return self();
    }

    @Step("Read program meeting count")
    public String getMeetingCount() {
        return getValue(meetingCount);
    }

    @Step("Set program meeting count")
    public T setMeetingCount(String value) {
        fillInput(meetingCount, value);
        return self();
    }

    /** Requests closing without discarding changes automatically. Empty means the clean form closed. */
    @Step("Request program modal close and inspect confirmation")
    public Optional<ConfirmationModal> requestCloseWithConfirmation() {
        requestClose();
        return wait.until(d -> {
            WebElement confirmation = ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(ConfirmationModal.ROOT_SELECTOR)).apply(d);
            if (confirmation != null) {
                return Optional.of(new ConfirmationModal(confirmation));
            }
            return ExpectedConditions.invisibilityOf(rootElement).apply(d) ? Optional.empty() : null;
        });
    }

    /** Closes a clean form. For dirty forms use requestCloseWithConfirmation() and choose explicitly. */
    @Step("Close program modal")
    public AdminProgramsPage close() {
        requestClose();
        waitUntilClosed();
        return new AdminProgramsPage(driver).waitUntilVisible();
    }
}
