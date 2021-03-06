package com.study.movieland.web.converter;

import com.study.movieland.entity.Currency;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class CurrencyParamConverter extends PropertyEditorSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setAsText(final String paramValue) {
        try {
            Currency currency = Currency.getValue(paramValue);
            setValue(currency);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid currency param {}", paramValue);
            throw new BadRequestParamException("Invalid currency param " + paramValue);
        }
    }

}
