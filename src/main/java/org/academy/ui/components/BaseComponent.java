package org.academy.ui.components;

import org.academy.ui.Base;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;

public class BaseComponent extends Base {
    protected WebElement rootElement;

    public BaseComponent(WebElement element) {
        super(((WrapsDriver) element).getWrappedDriver());
        this.rootElement = element;
        DefaultFieldDecorator decorator = new DefaultFieldDecorator(new DefaultElementLocatorFactory(element));
        // Keep the supplied root; decorate all other fields as usual.
        PageFactory.initElements((loader, field) ->
                field.getDeclaringClass() == BaseComponent.class && field.getName().equals("rootElement")
                        ? null : decorator.decorate(loader, field), this);
    }
}
