package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MetricType {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);

    private final int value;

    MetricType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static MetricType fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (MetricType type : MetricType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
