package org.academy.ui.components.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.admin.programs.ProgramEditorModal;
import org.academy.ui.modals.admin.programs.TranslateProgramModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProgramListItemComponent extends BaseComponent {

    @FindBy(css = ".program-info-identity > p")
    private WebElement title;

    @FindBy(css = ".program-info-description > p")
    private WebElement description;

    @FindBy(css = ".program-info-status .status > span:last-child")
    private WebElement statusText;

    @FindBy(css = "button[aria-label='Show additional information']")
    private WebElement informationButton;

    @FindBy(css = "button[aria-label^='Edit ']")
    private WebElement editButton;

    @FindBy(css = "button[aria-label='Translate']")
    private WebElement translateButton;

    @FindBy(css = "button[aria-label^='Delete ']")
    private WebElement deleteButton;

    public ProgramListItemComponent(WebElement rootElement) {
        super(rootElement);
    }

    /**
     * No wait: called inside the page's lookup wait.
     */
    public String getTitle() {
        return title.getText();
    }

    @Step("Read program card description")
    public String getDescription() {
        return getText(description);
    }

    @Step("Read program card status")
    public String getStatusText() {
        return getText(statusText);
    }

    @Step("Check whether program information is open")
    public boolean isInformationOpen() {
        return Boolean.parseBoolean(informationButton.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()));
    }

    @Step("Open program information")
    public ProgramInfoTooltipComponent openInformation() {
        if (!isInformationOpen()) {
            click(informationButton);
        }
        waitUntilAttributeEquals(informationButton, HtmlAttribute.ARIA_EXPANDED.getValue(), "true");

        // The tooltip is outside the card; its ID comes from the trigger.
        String tooltipId = waitUntilAttributeNotBlank(informationButton, HtmlAttribute.ARIA_DESCRIBED_BY.getValue());
        WebElement tooltip = waitUntilVisible(By.id(tooltipId));
        return new ProgramInfoTooltipComponent(tooltip);
    }

    @Step("Close program information")
    public ProgramListItemComponent closeInformation() {
        if (isInformationOpen()) {
            // Clicking the trigger again does not dismiss the tooltip.
            click(title);
        }
        waitUntilAttributeEquals(informationButton, HtmlAttribute.ARIA_EXPANDED.getValue(), "false");
        return this;
    }

    @Step("Open program deletion confirmation")
    public void openDeleteConfirmation() {
        click(deleteButton);
        // TODO: Return the shared confirmation component when available.
    }

    @Step("Open program editor")
    public ProgramEditorModal openEditModal() {
        click(editButton);
        WebElement modal = waitUntilVisible(By.cssSelector(ProgramEditorModal.ROOT_SELECTOR));
        return new ProgramEditorModal(modal);
    }

    @Step("Open program translation")
    public TranslateProgramModal openTranslateModal() {
        click(translateButton);
        WebElement modal = waitUntilVisible(By.cssSelector(TranslateProgramModal.ROOT_SELECTOR));
        return new TranslateProgramModal(modal);
    }
}
