package com.study.movieland.service;

import com.study.movieland.entity.Currency;

public interface CurrencyService {

    Double getConvertedPrice(double price, Currency currency);

}
