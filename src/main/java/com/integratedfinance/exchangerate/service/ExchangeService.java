package com.integratedfinance.exchangerate.service;

import java.math.BigDecimal;

import com.integratedfinance.exchangerate.model.ConversionResponse;
import com.integratedfinance.exchangerate.model.ExchangeRateResponse;
import com.integratedfinance.exchangerate.util.Currency;

public interface ExchangeService {

    ExchangeRateResponse getExchangeRate(Currency source, Currency target);

    ConversionResponse getConvertedValue(Currency source, Currency target, BigDecimal amount);

    ConversionResponse getManuallyConvertedValue(Currency source, Currency target, BigDecimal amount);
}
