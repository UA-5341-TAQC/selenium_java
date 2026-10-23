package org.academy.ui.types;

public enum TeamCategoryAction {
    ADD("Додати категорію"),
    EDIT("Редагувати"),
    DELETE("Видалити"),
    TRANSLATE("Додати переклад");

    private final String text;

    TeamCategoryAction(String text) {
        this.text = text;
    }

    // Повертає напис пункту контекстного меню.
    public String getText() {
        return text;
    }
}