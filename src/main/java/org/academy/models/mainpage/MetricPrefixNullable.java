package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MetricPrefixNullable {
    ZERO(0),
    ONE(1),
    TWO(2);

    private final int value;

    MetricPrefixNullable(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static MetricPrefixNullable fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (MetricPrefixNullable prefix : MetricPrefixNullable.values()) {
            if (prefix.value == value) {
                return prefix;
            }
        }
        return null;
    }
}
