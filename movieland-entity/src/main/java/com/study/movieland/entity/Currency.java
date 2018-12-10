package com.study.movieland.entity;

public enum Currency {
    UAH, USD, EUR;

    public static Currency getValue(String inputString) {
        for (Currency value : values()) {
            if (value.name().equalsIgnoreCase(inputString)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant " + inputString);
    }

    public static Boolean isExists(String inputString) {
        for (Currency value : values()) {
            if (value.name().equalsIgnoreCase(inputString)) {
                return true;
            }
        }
        return false;
    }

    public static Currency getDefault(){
        return Currency.UAH;
    }

}
