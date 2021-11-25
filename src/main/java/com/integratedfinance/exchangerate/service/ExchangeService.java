package com.integratedfinance.exchangerate.service;

import java.math.BigDecimal;

import com.integratedfinance.exchangerate.model.ConversionRequest;
import com.integratedfinance.exchangerate.model.ConversionResponse;
import com.integratedfinance.exchangerate.model.ExchangeRateResponse;
import com.integratedfinance.exchangerate.util.Currency;

public interface ExchangeService {

	public ExchangeRateResponse getExchangeRate(Currency source, Currency target);
	public ConversionResponse getConvertedValue(Currency source, Currency target, BigDecimal amount);
	public ConversionResponse getManuallyConvertedValue(Currency source, Currency target, BigDecimal amount);
}
