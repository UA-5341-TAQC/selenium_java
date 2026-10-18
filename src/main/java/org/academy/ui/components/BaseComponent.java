package org.academy.ui.components;

import org.academy.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class BaseComponent extends Base {
    protected WebElement rootElement;

    public BaseComponent(WebElement element) {
        super(extractDriver(element));
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
        this.rootElement = element;
    }

    private static WebDriver extractDriver(WebElement element) {
        WebElement current = element;
        while (current instanceof WrapsElement wrapped) {
            if (current instanceof WrapsDriver wd) {
                return wd.getWrappedDriver();
            }
            current = wrapped.getWrappedElement();
        }
        return ((WrapsDriver) current).getWrappedDriver();
    }
}
