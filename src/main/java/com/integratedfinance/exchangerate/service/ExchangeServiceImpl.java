package com.integratedfinance.exchangerate.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import com.integratedfinance.exchangerate.cache.RatesCache;
import com.integratedfinance.exchangerate.model.*;
import com.integratedfinance.exchangerate.repository.ExchangeRepository;
import com.integratedfinance.exchangerate.util.Constants;
import com.integratedfinance.exchangerate.util.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private Environment env;

    @Autowired
    private RatesCache cache;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    public ExchangeRateResponse getExchangeRate(Currency source, Currency target) {

        ExchangeRateResponse rateResponse = new ExchangeRateResponse();

        String rateKey = source.getCode() + target.getCode();

        BigDecimal cachedRate = cache.getCachedRate(rateKey);
        if (null != cachedRate) {
            rateResponse.setTargetRate(cachedRate);
            System.out.println("Returing data for " + rateKey + " from Cache");
            return rateResponse;
        }

        MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();
        uriVariables.add("access_key", env.getProperty(Constants.API_KEY));
        uriVariables.add("currencies", target.getCode());
        uriVariables.add("source", source.getCode());
        uriVariables.add("format", "1");

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.API_BASE_URL) + "live").queryParams(uriVariables).build();

        RestTemplate restTemplate = new RestTemplate();
        ExchangeRates rates = restTemplate.getForObject(uriComponents.toUri(), ExchangeRates.class);

        if (rates.getSuccess()) {
            String cRate = rates.getQuotes().get(rateKey);
            BigDecimal bdr = new BigDecimal(cRate);
            rateResponse.setTargetRate(bdr);
            cache.cacheRate(rateKey, bdr);
        } else {
            rateResponse.setError(rates.getError());
            rateResponse.setTargetRate(BigDecimal.ZERO);
        }

        return rateResponse;
    }

    @Override
    public ConversionResponse getConvertedValue(Currency source, Currency target, BigDecimal amount) {

        ConversionResponse conversionResponse = new ConversionResponse();

        MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();
        uriVariables.add("access_key", env.getProperty(Constants.API_KEY));
        uriVariables.add("from", source.getCode());
        uriVariables.add("to", target.getCode());
        uriVariables.add("amount", String.valueOf(amount));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.API_BASE_URL) + "convert").queryParams(uriVariables).build();

        RestTemplate restTemplate = new RestTemplate();
        ConversionRates rates = restTemplate.getForObject(uriComponents.toUri(), ConversionRates.class);

        if (rates != null && rates.getSuccess()) {
            String cRate = rates.getResult();
            BigDecimal bdr = new BigDecimal(cRate);
            conversionResponse.setConvertedValue(bdr);
        } else {
            conversionResponse.setError(rates.getError());
            conversionResponse.setConvertedValue(BigDecimal.ZERO);
        }

        return conversionResponse;
    }


    @Override
    public ConversionResponse getManuallyConvertedValue(Currency source, Currency target, BigDecimal amount) {

        ConversionResponse conversionResponse = new ConversionResponse();

        String rateKey = source.getCode() + target.getCode();

        MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();
        uriVariables.add("access_key", env.getProperty(Constants.API_KEY));
        uriVariables.add("currencies", target.getCode());
        uriVariables.add("source", source.getCode());
        uriVariables.add("format", "1");

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.API_BASE_URL) + "live").queryParams(uriVariables).build();

        RestTemplate restTemplate = new RestTemplate();
        ConversionRates rates = restTemplate.getForObject(uriComponents.toUri(), ConversionRates.class);

        if (rates.getSuccess()) {
            String cRate = rates.getQuotes().get(rateKey);
            BigDecimal bdr = new BigDecimal(cRate);
            conversionResponse.setConvertedValue(bdr.multiply(amount));
            conversionResponse.setTransactionId(saveConversionExchange(source, target, amount, conversionResponse, rates).getId());
        } else {
            conversionResponse.setError(rates.getError());
            conversionResponse.setConvertedValue(BigDecimal.ZERO);
        }

        return conversionResponse;
    }

    private ExchangeConversion saveConversionExchange(Currency source, Currency target, BigDecimal amount, ConversionResponse conversionResponse, ConversionRates rates) {
        ExchangeConversion exchangeConversion = new ExchangeConversion();
        exchangeConversion.setSrcCurrency(source);
        exchangeConversion.setTgtCurrency(target);
        exchangeConversion.setAmount(amount);
        exchangeConversion.setConvertedValue(conversionResponse.getConvertedValue());
        LocalDate date =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(rates.getTimestamp()), TimeZone
                        .getDefault().toZoneId()).toLocalDate();
        exchangeConversion.setConversionDate(date);
        exchangeRepository.save(exchangeConversion);
        return exchangeConversion;
    }

}
