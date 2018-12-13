package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.data.report.Report;
import com.study.movieland.data.report.ReportOutputType;
import com.study.movieland.data.report.ReportStatus;
import com.study.movieland.data.report.ReportType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRowMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        Report report = new Report();
        report.setId(rs.getInt("id"));
        report.setReportType(ReportType.getValue(rs.getString("report_type")));
        report.setReportOutputType(ReportOutputType.getValue(rs.getString("report_format")));
        report.setStatus(ReportStatus.getValue(rs.getString("status")));
        Date sqlDateFrom = rs.getDate("date_from");
        if (sqlDateFrom != null) {
            report.setDateFrom(sqlDateFrom.toLocalDate());
        }
        Date sqlDateTo = rs.getDate("date_to");
        if (sqlDateTo != null) {
            report.setDateTo(sqlDateTo.toLocalDate());
        }
        report.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        report.setPathToFile(rs.getString("path_to_file"));
        return report;
    }

}