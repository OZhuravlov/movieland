package com.study.movieland.service.impl;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import com.study.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCurrencyService implements CurrencyService {

    CurrencyDao currencyDao;

    @Override
    public Double getConvertedPrice(double price, Currency currency) {
        double convertedPrice = price / currencyDao.getRate(currency);
        return Math.round(convertedPrice * 100d) / 100d;
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
}
