package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class DetailedProgramSectionComponent extends BaseComponent {

    @FindBy(css = "div[class*='DetailedProgramSection_container']")
    private List<WebElement> detailProgramContainerElements;

    public DetailedProgramSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Detailed program container count")
    public int getDetProgramContainerCount() {
        return detailProgramContainerElements.size();
    }


    @Step("Get list of Detailed program containers components")
    public List<DetailProgramContainerComponent> getDetailProgramContainers() {
        return detailProgramContainerElements.stream()
                .map(DetailProgramContainerComponent::new)
                .collect(Collectors.toList());
    }

    @Step("Get Detailed program container component by index: {index}")
    public DetailProgramContainerComponent getDetailProgramContainerIndex(int index) {
        if (index < 0 || index >= detailProgramContainerElements.size()) {
            throw new IndexOutOfBoundsException("Invalid ProgramCard index: " + index + ", total cards: " + detailProgramContainerElements.size());
        }
        return new DetailProgramContainerComponent(detailProgramContainerElements.get(index));
    }

}
