package org.academy.ui.modals.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

/**
 * Simple deletion dialogs used by FAQ, programs, feedback, team members and
 * partner sections. Category-selection forms and wrapped report actions have
 * different structures and are not represented by this component.
 */
public class DeleteConfirmationModal extends ConfirmationModal {

    private static final String ROOT_XPATH =
            "//span[contains(@class,'modal-header-text') and normalize-space()=%s]"
                    + "/ancestor::div[contains(concat(' ',normalize-space(@class),' '),' modal-container ')][1]";

    public DeleteConfirmationModal(WebElement rootElement) {
        super(rootElement);
    }

    /** The exact title distinguishes deletion from other open confirmation dialogs. */
    public static DeleteConfirmationModal waitFor(WebDriverWait wait, String expectedTitle) {
        Objects.requireNonNull(expectedTitle, "Deletion title must not be null");
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                getLocatorByTitle(expectedTitle)));
        return new DeleteConfirmationModal(root);
    }

    private static By getLocatorByTitle(String title) {
        return By.xpath(String.format(ROOT_XPATH, Quotes.escape(title)));
    }
}
