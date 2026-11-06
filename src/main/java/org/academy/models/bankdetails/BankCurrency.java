package org.academy.models.bankdetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BankCurrency {
    UAH(0),
    USD(1),
    EUR(2);

    private final int value;

    BankCurrency(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static BankCurrency fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (BankCurrency currency : BankCurrency.values()) {
            if (currency.value == value) {
                return currency;
            }
        }
        return null;
    }
}
