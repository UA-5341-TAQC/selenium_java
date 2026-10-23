package org.academy.ui.types;

public enum ProgramTypes {
    CHILDREN(0, "Дитяча програма", "Children's program"),
    VETERAN(1, "Ветеранська програма", null),
    ALL(2, "Всі", "All");

    private final int position;
    private final String uaLabel;
    private final String enLabel;

    ProgramTypes(int position, String uaLabel, String enLabel) {
        this.position = position;
        this.uaLabel = uaLabel;
        this.enLabel = enLabel;
    }

    public int getPosition() {
        return position;
    }

    public String getUaLabel() {
        return uaLabel;
    }

    public String getEnLabel() {
        if (enLabel == null) {
            throw new IllegalStateException("English label is not defined yet for " + name());
        }
        return enLabel;
    }
}
