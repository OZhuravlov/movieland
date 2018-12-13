package com.study.movieland.web.converter;

import com.study.movieland.data.report.ReportType;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class ReportTypeConverter extends PropertyEditorSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setAsText(final String paramValue) {
        ReportType reportType = ReportType.getValue(paramValue);
        if (reportType == null) {
            logger.warn("Invalid report type param {}", paramValue);
            throw new BadRequestParamException("Invalid report type param " + paramValue);
        }
        setValue(reportType);
    }

}
