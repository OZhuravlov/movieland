package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.ReportDao;
import com.study.movieland.dao.jdbc.mapper.ReportRowMapper;
import com.study.movieland.data.report.Report;
import com.study.movieland.data.report.ReportStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class JdbcReportDao implements ReportDao {

    private static final String GET_BY_ID_SQL =
            "SELECT id, report_type, report_format, status, date_from, date_to, last_updated, path_to_file FROM reports WHERE id = ?";
    private static final String GET_STATUS_BY_ID_SQL =
            "SELECT status FROM reports WHERE id = ?";
    private static final String GET_PATH_TO_FILE_BY_ID_SQL =
            "SELECT path_to_file FROM reports WHERE id = ?";
    private static final String ADD_SQL = "INSERT INTO reports (report_type, report_format, status, date_from, date_to)" +
            " VALUES(:report_type, :report_format, :status, :date_from, :date_to)";
    private static final String GET_DELETE_BY_ID_SQL = "DELETE FROM reports WHERE id = ?";
    private static final String SET_STATUS_SQL = "UPDATE reports SET status = :status, last_updated = :last_updated WHERE id = :id";
    private static final String SET_STATUS_AND_FILE_PATH_SQL = "UPDATE reports SET status = :status, path_to_file = :path_to_file, last_updated = :last_updated WHERE id = :id";
    private static final ReportRowMapper REPORT_ROW_MAPPER = new ReportRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int addRequest(Report report) {
        logger.info("add new report: {}", report);
        MapSqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("report_type", report.getReportType())
                .addValue("report_format", report.getReportOutputType())
                .addValue("status", report.getStatus())
                .addValue("date_from", report.getDateFrom())
                .addValue("date_to", report.getDateTo());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(ADD_SQL, queryParams, keyHolder, new String[] {"id"});
        report.setId((int) keyHolder.getKey());
        return report.getId();
    }

    @Override
    public ReportStatus checkStatus(int id) {
        logger.info("check status of report: {}", id);
        return jdbcTemplate.queryForObject(GET_STATUS_BY_ID_SQL, ReportStatus.class, id);
    }

    @Override
    public String getPathToFile(int id) {
        logger.info("get path to file of report: {}", id);
        return jdbcTemplate.queryForObject(GET_PATH_TO_FILE_BY_ID_SQL, String.class, id);
    }

    @Override
    public Report get(int id) {
        logger.info("get path to file of report: {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID_SQL, Report.class, id);
    }

    @Override
    public void remove(int id) {
        jdbcTemplate.update(GET_DELETE_BY_ID_SQL, id);
    }

    @Override
    public void setStatus(int id, ReportStatus status) {
        jdbcTemplate.update(SET_STATUS_SQL, status.toString(), LocalDateTime.now(), id);
    }

    @Override
    public void setDone(int id, String pathToFile) {
        jdbcTemplate.update(SET_STATUS_AND_FILE_PATH_SQL, ReportStatus.DONE, pathToFile, LocalDateTime.now(), id);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
