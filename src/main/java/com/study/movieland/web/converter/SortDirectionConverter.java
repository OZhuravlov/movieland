package com.study.movieland.web.converter;

import com.study.movieland.entity.SortDirection;
import com.study.movieland.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class SortDirectionConverter extends PropertyEditorSupport {

    private static final String DEFAULT_REQUEST_SORT_PARAM_VALUE = "NONE";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setAsText(final String paramValue) {
        if (DEFAULT_REQUEST_SORT_PARAM_VALUE.equalsIgnoreCase(paramValue)) {
            return;
        }
        try {
            SortDirection sortDirection = SortDirection.valueOf(paramValue.toUpperCase());
            setValue(sortDirection);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid sort direction param {}", paramValue);
            throw new BadRequestParamException("Invalid sort direction param");
        }
    }
}
