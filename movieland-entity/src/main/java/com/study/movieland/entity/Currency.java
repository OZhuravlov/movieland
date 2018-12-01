package com.study.movieland.entity;

public enum Currency {
    UAH, USD, EUR;

    public static Currency getValue(String inputString) {
        for (Currency value : values()) {
            if (value.name().equalsIgnoreCase(inputString)) {
                return value;
            }
        }
        return null;
    }
}
