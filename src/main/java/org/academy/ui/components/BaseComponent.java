package org.academy.ui.components;

import org.academy.ui.Base;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class BaseComponent extends Base {
    protected WebElement rootElement;

    public BaseComponent(WebElement element) {
        super(((WrapsDriver) element).getWrappedDriver());
        this.rootElement = element;
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }
}
