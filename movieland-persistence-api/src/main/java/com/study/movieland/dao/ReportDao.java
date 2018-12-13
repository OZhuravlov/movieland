package com.study.movieland.dao;

import com.study.movieland.data.report.Report;
import com.study.movieland.data.report.ReportStatus;

public interface ReportDao {

    int addRequest(Report report);

    ReportStatus checkStatus(int id);

    String getPathToFile(int id);

    Report get(int id);

    void remove(int id);

    void setStatus(int id, ReportStatus status);

    void setDone(int id, String pathToFile);
}
