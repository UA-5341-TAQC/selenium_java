package org.academy.ui.constants;

public enum HtmlAttribute {

    ARIA_EXPANDED("aria-expanded"),
    ARIA_SELECTED("aria-selected"),
    ARIA_DESCRIBED_BY("aria-describedby"),
    ARIA_REQUIRED("aria-required"),
    ARIA_INVALID("aria-invalid"),
    REQUIRED("required"),
    READ_ONLY("readonly"),
    PLACEHOLDER("placeholder"),
    ACCEPT("accept"),
    CLASS("class"),
    NAME("name"),
    FOR("for"),
    MAX_LENGTH("maxlength"),
    DRAGGABLE("draggable"),
    SRC("src");

    private final String value;

    HtmlAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
