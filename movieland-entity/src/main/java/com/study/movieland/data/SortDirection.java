package com.study.movieland.data;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection getValue(String inputString) {
        for (SortDirection value : values()) {
            if (value.name().equalsIgnoreCase(inputString)) {
                return value;
            }
        }
        return null;
    }
}
