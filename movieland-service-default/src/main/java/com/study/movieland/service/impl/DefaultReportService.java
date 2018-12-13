package com.study.movieland.service.impl;

import com.study.movieland.dao.ReportDao;
import com.study.movieland.data.report.Report;
import com.study.movieland.data.report.ReportStatus;
import com.study.movieland.service.MovieService;
import com.study.movieland.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultReportService implements ReportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;
    private ReportDao reportDao;

    @Override
    public int addReportRequest(Report report) {
        return reportDao.addRequest(report);
    }

    @Override
    public ReportStatus checkReportStatus(int id) {
        return reportDao.checkStatus(id);
    }

    @Override
    public String getReportLink(int id) {
        return reportDao.getPathToFile(id);
    }

    @Override
    public void deleteRequestOrReport(int id) {
        reportDao.remove(id);
    }

    @Override
    public Report getReport(String filename) {
        return null;
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Autowired
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }
}