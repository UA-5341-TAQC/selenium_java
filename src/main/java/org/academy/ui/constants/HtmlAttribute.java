package org.academy.ui.constants;

public enum HtmlAttribute {

    ARIA_EXPANDED("aria-expanded"),
    ARIA_SELECTED("aria-selected"),
    ARIA_DESCRIBED_BY("aria-describedby"),
    CLASS("class"),
    NAME("name"),
    FOR("for"),
    SRC("src");

    private final String value;

    HtmlAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
