package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.academy.ui.components.detailprogram.DetailedProgramHeaderComponent;
import org.academy.ui.components.detailprogram.DetailedProgramSectionComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DetailProgramPage extends BasePublicPage{

    @FindBy(css = "div[class*='DetailedProgramHeader']")
    private WebElement DetailedProgramHeaderElement;

    @FindBy(css = "div[class*='DetailedProgramPageContent_sections']")
    private WebElement DetailedProgramSectionElement;

    @FindBy(css = "section[class*='CtaSection_root']")
    private WebElement CtaSectionElement;

    public DetailProgramPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get DetailedProgramHeader component")
    public DetailedProgramHeaderComponent getDetailProgramHeaderSection() {
        return new DetailedProgramHeaderComponent(waitUntilVisible(DetailedProgramHeaderElement));
    }

    @Step("Get DetailedProgramSection component")
    public DetailedProgramSectionComponent getDetailedProgramSection() {
        return new DetailedProgramSectionComponent(waitUntilVisible(DetailedProgramSectionElement));
    }

    @Step("Get IntroSection component")
    public CtaSectionComponent getCtaSection() {
        return new CtaSectionComponent(waitUntilVisible(CtaSectionElement));
    }

}
