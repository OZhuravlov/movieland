package com.study.movieland.dao.rest;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.data.NbuCurrencyRate;
import com.study.movieland.entity.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class NbuCurrencyDao implements CurrencyDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RestTemplate restTemplate;
    private Currency baseCurrency;
    private String url;

    @Override
    public Map<Currency, Double> getAll() {
        logger.info("get All rates");
        Map<Currency, Double> rates = new HashMap<>();
        for (Currency currency : Currency.values()) {
            Double rate = getRate(currency);
            rates.put(currency, rate);
        }
        return rates;
    }

    @Override
    public double getRate(Currency currency) {
        logger.info("getting rate by currency {} from cache", currency);
        Double rate;
        if (currency.equals(baseCurrency)) {
            rate = 1d;
            logger.info("set rate {} for Base currency {}", rate, baseCurrency);
            return rate;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String exchangeDate = formatter.format(new Date());
        NbuCurrencyRate[] nbuCurrencyRates = restTemplate.getForObject(url, NbuCurrencyRate[].class, currency.name(), exchangeDate);
        logger.debug("got currency rate for {}: {}", currency, nbuCurrencyRates[0]);
        rate = nbuCurrencyRates[0].getRate();
        logger.info("got rate {}", rate);
        return nbuCurrencyRates[0].getRate();
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
