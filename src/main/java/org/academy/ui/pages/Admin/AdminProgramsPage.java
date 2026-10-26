package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.ProgramListItemComponent;
import org.academy.ui.modals.admin.programs.ProgramEditorModal;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class AdminProgramsPage extends BaseAdminPage {

    @FindBy(css = "[data-testid='programs-page-content']")
    private WebElement pageContent;

    @FindBy(css = "[data-testid='programs-page-content'] .program-item")
    private List<WebElement> programItems;

    @FindBy(css = "[data-testid='programs-page-content'] .admin-panel-toolbar-actions button.btn-primary")
    private WebElement addProgramButton;

    public AdminProgramsPage(WebDriver driver) {
        super(driver);
    }

    public AdminProgramsPage waitUntilVisible() {
        waitUntilVisible(pageContent);
        return this;
    }

    @Step("Open add program modal")
    public ProgramEditorModal openAddProgramModal() {
        click(addProgramButton);
        WebElement modal = waitUntilVisible(By.cssSelector(ProgramEditorModal.ROOT_SELECTOR));
        return new ProgramEditorModal(modal);
    }

    /**
     * Matches loaded items only; does not scroll or change filters.
     */
    @Step("Find loaded program by title: {title}")
    public ProgramListItemComponent getProgramByTitle(String title) {
        Objects.requireNonNull(title, "Program title must not be null");
        waitUntilVisible();

        return wait.until(currentDriver -> {
            try {
                ProgramListItemComponent match = null;
                for (WebElement item : programItems) {
                    ProgramListItemComponent program = new ProgramListItemComponent(item);
                    if (title.equals(program.getTitle())) {
                        if (match != null) {
                            throw new IllegalStateException("Multiple loaded programs have title: " + title);
                        }
                        match = program;
                    }
                }
                return match;
            } catch (StaleElementReferenceException exception) {
                // Retry after the list re-renders.
                return null;
            }
        });
    }
}
