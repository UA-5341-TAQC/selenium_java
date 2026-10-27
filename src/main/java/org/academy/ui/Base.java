package org.academy.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class Base {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    protected WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected List<WebElement> waitUntilAllVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    protected WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitUntilInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitUntilInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitUntilAttributeEquals(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    protected void waitUntilAttributeNotContains(WebElement element, String attribute, String value) {
        wait.until(currentDriver -> {
            String actual = element.getDomAttribute(attribute);
            return actual == null || !actual.contains(value);
        });
    }

    protected String waitUntilAttributeNotBlank(WebElement element, String attribute) {
        return wait.until(currentDriver -> {
            String value = element.getDomAttribute(attribute);
            return value == null || value.isBlank() ? null : value;
        });
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isVisible(WebElement element) {
        try {
            return waitUntilVisible(element).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    protected void hover(WebElement element) {
        scrollIntoView(element);
        actions.moveToElement(waitUntilVisible(element)).perform();
    }

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void click(WebElement element) {
        waitUntilClickable(element).click();
    }

    protected void uploadFile(WebElement input, Path file) {
        Objects.requireNonNull(file, "File path must not be null");
        Path absolutePath = file.toAbsolutePath().normalize();
        if (!Files.isRegularFile(absolutePath) || !Files.isReadable(absolutePath)) {
            throw new IllegalArgumentException("File is not readable: " + absolutePath);
        }
        // File inputs may be hidden.
        wait.until(currentDriver -> input.isEnabled());
        input.sendKeys(absolutePath.toString());
    }

    protected String getText(WebElement element) {
        return waitUntilVisible(element).getText().trim();
    }

    protected List<String> getTexts(List<WebElement> elements) {
        return elements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    protected WebElement findByText(List<WebElement> elements, String text) {
        Objects.requireNonNull(text, "Text must not be null");
        String expected = text.trim();
        try {
            return wait.until(currentDriver -> elements.stream()
                    .filter(element -> hasText(element, expected))
                    .findFirst()
                    .orElse(null));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element with text not found: " + text);
        }
    }

    private boolean hasText(WebElement element, String expected) {
        try {
            return element.getText().trim().equalsIgnoreCase(expected);
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    protected String getValue(WebElement element) {
        return waitUntilVisible(element).getDomProperty("value");
    }

    protected String getDomAttribute(WebElement element, String attribute) {
        return waitUntilVisible(element).getDomAttribute(attribute);
    }

    protected void fillInput(WebElement element, String value) {
        Objects.requireNonNull(value, "Input value must not be null");
        scrollIntoView(element);
        WebElement input = waitUntilClickable(element);
        Keys modifier = System.getProperty("os.name").startsWith("Mac") ? Keys.COMMAND : Keys.CONTROL;
        input.click();
        actions.keyDown(modifier).sendKeys("a").keyUp(modifier).perform();
        input.sendKeys(Keys.BACK_SPACE);
        if (!value.isEmpty()) {
            input.sendKeys(value);
        }
    }

    protected void clearInput(WebElement element) {
        fillInput(element, "");
    }

    protected void blur(WebElement element) {
        waitUntilVisible(element).sendKeys(Keys.TAB);
    }
}

