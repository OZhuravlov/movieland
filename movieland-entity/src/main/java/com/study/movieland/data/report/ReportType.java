package com.study.movieland.data.report;

public enum ReportType {
    ALL_MOVIES("allMovies"),
    ADDED_DURING_PERIOD("addedDuringPeriod"),
    TOP_ACTIVE_USERS("topActiveUsers");

    private String name;

    ReportType(String stringValue) {
        this.name = stringValue;
    }

    public static ReportType getValue(String inputString){
        for (ReportType value : values()) {
            if(value.name.equalsIgnoreCase(inputString)){
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant " + inputString);
    }
}
