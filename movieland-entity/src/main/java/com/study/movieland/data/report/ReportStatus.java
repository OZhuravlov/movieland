package com.study.movieland.data.report;

public enum ReportStatus {
    NEW, RUNNING, FAILED, CANCELLED, DONE;

    public static ReportStatus getValue(String inputString){
        for (ReportStatus value : values()) {
            if(value.toString().equalsIgnoreCase(inputString)){
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant " + inputString);
    }
}
