package com.study.movieland.data.report;

public enum ReportOutputType {
    XLSX, PDF;

    public static ReportOutputType getValue(String inputString){
        for (ReportOutputType value : values()) {
            if(value.toString().equalsIgnoreCase(inputString)){
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant " + inputString);
    }
}
