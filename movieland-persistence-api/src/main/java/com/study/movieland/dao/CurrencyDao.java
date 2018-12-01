package com.study.movieland.dao;

import com.study.movieland.entity.Currency;

import java.util.Map;

public interface CurrencyDao {

    Map<Currency, Double> getAll();

    double getRate(Currency currency);

}
