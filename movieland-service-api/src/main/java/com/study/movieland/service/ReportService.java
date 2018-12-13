package com.study.movieland.service;

import com.study.movieland.data.report.Report;
import com.study.movieland.data.report.ReportStatus;

public interface ReportService {

    int addReportRequest(Report report);

    ReportStatus checkReportStatus(int id);

    String getReportLink(int id);

    void deleteRequestOrReport(int id);

    Report getReport(String filename);
}
