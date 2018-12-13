package com.study.movieland.web.controller;

import com.study.movieland.data.report.*;
import com.study.movieland.entity.Role;
import com.study.movieland.service.ReportService;
import com.study.movieland.web.annotation.ProtectedBy;
import com.study.movieland.web.converter.ReportOutputTypeConverter;
import com.study.movieland.web.converter.ReportTypeConverter;
import com.study.movieland.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ReportService reportService;
    private ServletContext servletContext;

    @RequestMapping(path = "/{reportType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public String AddReportRequest(@PathVariable("reportType") ReportType reportType,
                                   @RequestParam(value = "format") ReportOutputType reportOutputType,
                                   @RequestParam(value = "from", required = false)
                                   @DateTimeFormat(pattern = "yyyyMMdd") LocalDate dateFrom,
                                   @RequestParam(value = "to", required = false)
                                   @DateTimeFormat(pattern = "yyyyMMdd") LocalDate dateTo
    ) {
        logger.info("Add request of report");
        Report report = new Report();
        report.setReportType(reportType);
        report.setReportOutputType(reportOutputType);
        report.setDateFrom(dateFrom);
        report.setDateTo(dateTo);
        int reportId = reportService.addReportRequest(report);
        return "{\"reportId\" : " + reportId + "\"}";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public String checkReportStatus(@PathVariable int id) {
        logger.info("Check report status");
        ReportStatus status = reportService.checkReportStatus(id);
        return "{\"status\" : " + status + "\"}";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public String getReportLink(@PathVariable int id) {
        logger.info("Get link to report");
        String url = reportService.getReportLink(id);
        return "{\"reportLink\" : " + url + "\"}";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public void deleteRequestOrReport(@PathVariable int id) {
        logger.info("Get request or report");
        reportService.deleteRequestOrReport(id);
    }

    @RequestMapping(path = "/download/{filename}", method = RequestMethod.GET)
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public ResponseEntity getReport(@PathVariable String filename) throws FileNotFoundException {
        logger.info("Get request or report");
        Report report = reportService.getReport(filename);
        String pathToFile = report.getPathToFile();
        MediaType mediaType = WebUtils.getMediaTypeForFileName(servletContext, pathToFile);
        logger.info("File {}, MediaType {}", pathToFile, mediaType);
        File file = new File(pathToFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(ReportType.class, new ReportTypeConverter());
        webdataBinder.registerCustomEditor(ReportOutputType.class, new ReportOutputTypeConverter());
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
