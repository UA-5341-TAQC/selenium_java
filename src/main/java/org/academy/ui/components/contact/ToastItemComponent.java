package org.academy.ui.components.contact;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;

public class ToastItemComponent extends BaseComponent {

    public ToastItemComponent(WebElement element) {
        super(element);
    }

    @Step("Check if toast notification is displayed")
    public boolean isDisplayed() {
        return isDisplayed(rootElement);
    }

    @Step("Get toast message text")
    public String getMessageText() {
        return getText(rootElement);
    }

    @Step("Get toast type or class attribute")
    public String getToastType() {
        String clazz = getDomAttribute(rootElement, "class");
        return clazz != null ? clazz : "";
    }
}
