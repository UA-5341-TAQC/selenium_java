package org.academy.ui.modals.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.pages.Admin.AdminProgramsPage;
import org.academy.ui.types.ProgramField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            names.add(field.getDomAttribute("name"));
        }
        return names;
    }

    @Step("Read character counter for program field {field}")
    public String getCharacterCounter(ProgramField field) {
        Objects.requireNonNull(field, "Program field must not be null");
        for (WebElement counter : characterCounters) {
            if (field.getId().equals(counter.getDomAttribute("for"))) {
                return getText(counter);
            }
        }
        throw new IllegalStateException("No character counter for field: " + field);
    }

    @Step("Read program name")
    public String getName() {
        return getValue(name);
    }

    @Step("Read program description")
    public String getDescription() {
        return getValue(description);
    }

    @Step("Read program location")
    public String getLocation() {
        return getValue(location);
    }

    @Step("Read program participant count")
    public String getParticipantsCount() {
        return getValue(participantsCount);
    }

    @Step("Read program meeting count")
    public String getMeetingCount() {
        return getValue(meetingCount);
    }

    @Step("Set program name")
    public T setName(String value) {
        fillInput(name, value);
        return self();
    }

    @Step("Set program description")
    public T setDescription(String value) {
        fillInput(description, value);
        return self();
    }

    @Step("Set program location")
    public T setLocation(String value) {
        fillInput(location, value);
        return self();
    }

    @Step("Set program participant count")
    public T setParticipantsCount(String value) {
        fillInput(participantsCount, value);
        return self();
    }

    @Step("Set program meeting count")
    public T setMeetingCount(String value) {
        fillInput(meetingCount, value);
        return self();
    }

    /** Dirty forms require shared confirmation handling. */
    @Step("Close program modal")
    public AdminProgramsPage close() {
        requestClose();
        waitUntilClosed();
        return new AdminProgramsPage(driver).waitUntilVisible();
    }
}
