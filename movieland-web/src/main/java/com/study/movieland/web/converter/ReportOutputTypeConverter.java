package com.study.movieland.web.converter;

import com.study.movieland.data.report.ReportOutputType;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class ReportOutputTypeConverter extends PropertyEditorSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setAsText(final String paramValue) {
        ReportOutputType reportType = ReportOutputType.getValue(paramValue);
        if (reportType == null) {
            logger.warn("Invalid report output type param {}", paramValue);
            throw new BadRequestParamException("Invalid report output type param " + paramValue);
        }
        setValue(reportType);
    }

}
