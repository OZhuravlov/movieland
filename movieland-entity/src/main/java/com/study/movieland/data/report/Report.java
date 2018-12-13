package com.study.movieland.data.report;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Report {
    int id;
    ReportType reportType;
    ReportOutputType reportOutputType;
    ReportStatus status;
    LocalDate dateFrom;
    LocalDate dateTo;
    LocalDateTime lastUpdated;
    String pathToFile;
}
