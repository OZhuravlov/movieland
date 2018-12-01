package com.study.movieland.dao.rest;

import com.study.movieland.data.NbuCurrencyRate;
import com.study.movieland.entity.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NbuCurrencyDaoTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NbuCurrencyDao nbuCurrencyDao = new NbuCurrencyDao();

    @Test
    public void getRateTest() {
        nbuCurrencyDao.setUrl("http://test.com");
        nbuCurrencyDao.setBaseCurrency(Currency.UAH);
        NbuCurrencyRate[] nbuCurrencyRateUSD
                = new NbuCurrencyRate[]{new NbuCurrencyRate(840, "Долар США", 28.390932, "USD", "01.12.2018")};
        NbuCurrencyRate[] nbuCurrencyRateEUR
                = new NbuCurrencyRate[]{new NbuCurrencyRate(978, "Євро", 32.328754, "EUR", "01.12.2018")};
        Mockito.when(
                restTemplate.getForObject(Mockito.anyString(),
                        any(Class.class),
                        any(Class.class),
                        Mockito.anyString()))
                .thenReturn(nbuCurrencyRateUSD);
        double rateUSD = nbuCurrencyDao.getRate(Currency.USD);
        assertEquals(0, Double.compare(nbuCurrencyRateUSD[0].getRate(), rateUSD));

        Mockito.when(
                restTemplate.getForObject(Mockito.anyString(),
                        any(Class.class),
                        any(Class.class),
                        Mockito.anyString()))
                .thenReturn(nbuCurrencyRateEUR);
        double rateEUR = nbuCurrencyDao.getRate(Currency.EUR);
        assertEquals(0, Double.compare(nbuCurrencyRateEUR[0].getRate(), rateEUR));

        Mockito.verify(restTemplate, times(2))
                .getForObject(anyString(), any(Class.class), anyString(), anyString());
        verifyNoMoreInteractions(restTemplate);
    }
}