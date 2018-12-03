package com.study.movieland.service.impl;

import com.study.movieland.data.NbuCurrencyRate;
import com.study.movieland.entity.Currency;
import com.study.movieland.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NbuCurrencyService implements CurrencyService {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile Map<Currency, Double> rates;
    private RestTemplate restTemplate;
    private Currency baseCurrency;
    private String url;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduler.dao.cache.currency.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.dao.cache.currency.initDelayInMilliseconds}")
    public void init() {
        logger.info("Refreshing cache");
        Map<Currency, Double> rateMap = getAllRates();
        logger.info("Got {} rates from dao", rateMap.size());
        rates = rateMap;
    }

    @Override
    public Double getConvertedPrice(double price, Currency currency) {
        if(currency.equals(baseCurrency)){
            return price;
        }
        double convertedPrice = price / rates.get(currency);
        return convertedPrice;
    }

    Map<Currency, Double> getAllRates() {
        logger.info("getting rate for all currencies");
        String exchangeDate = FORMATTER.format(new Date());
        NbuCurrencyRate[] nbuCurrencyRates = restTemplate.getForObject(url, NbuCurrencyRate[].class, exchangeDate);
        logger.trace("got currency rates: {}", Arrays.asList(nbuCurrencyRates));

        Map<Currency, Double> rateMap = new HashMap<>();
        for (NbuCurrencyRate nbuCurrencyRate : nbuCurrencyRates) {
            String currencyCode = nbuCurrencyRate.getCurrencyCode();
            if(Currency.isExists(currencyCode)){
                rateMap.put(Currency.getValue(currencyCode), nbuCurrencyRate.getRate());
            }
        }
        logger.debug("Currency rates from NBU {}", rateMap);
        return rateMap;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${dao.currency.base:UAH}")
    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Value("${dao.currency.url}")
    public void setUrl(String url) {
        this.url = url;
    }
}
