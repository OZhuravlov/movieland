package com.study.movieland.service.impl;

import com.study.movieland.data.NbuCurrencyRate;
import com.study.movieland.entity.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NbuCurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NbuCurrencyService currencyService = new NbuCurrencyService(Currency.UAH);

    @Test
    public void initAndGetAllTest() {
        currencyService.setUrl("http://test.com");
        NbuCurrencyRate[] nbuCurrencyRates
                = new NbuCurrencyRate[]{new NbuCurrencyRate(840, "Долар США", 28.28, "USD", "01.12.2018"),
                new NbuCurrencyRate(978, "Євро", 32.32, "EUR", "01.12.2018")};
        Mockito.when(
                restTemplate.getForObject(Mockito.anyString(),
                        any(Class.class),
                        Mockito.anyString()))
                .thenReturn(nbuCurrencyRates);
        currencyService.init();
        Map<Currency, Double> currencies = currencyService.getAllRates();
        assertEquals(2, currencies.size());
        assertThat(currencies, is(currencyService.getAllRates()));
    }


    @Test
    public void getAllRatesTest() {
        currencyService.setUrl("http://test.com");
        NbuCurrencyRate[] nbuCurrencyRates
                = new NbuCurrencyRate[]{new NbuCurrencyRate(840, "Долар США", 28.28, "USD", "01.12.2018"),
                new NbuCurrencyRate(978, "Євро", 32.32, "EUR", "01.12.2018")};
        Mockito.when(
                restTemplate.getForObject(Mockito.anyString(),
                        any(Class.class),
                        Mockito.anyString()))
                .thenReturn(nbuCurrencyRates);
        Map<Currency, Double> rates = currencyService.getAllRates();
        Mockito.verify(restTemplate, times(1))
                .getForObject(anyString(), any(Class.class), anyString());
        verifyNoMoreInteractions(restTemplate);

        double rateUSD = rates.get(Currency.USD);
        assertEquals(0, Double.compare(nbuCurrencyRates[0].getRate(), rateUSD));

        double rateEUR = rates.get(Currency.EUR);
        assertEquals(0, Double.compare(nbuCurrencyRates[1].getRate(), rateEUR));
    }

}