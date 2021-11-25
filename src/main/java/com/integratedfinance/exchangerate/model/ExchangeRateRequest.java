package com.integratedfinance.exchangerate.model;

import com.integratedfinance.exchangerate.util.Currency;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ExchangeRateRequest {

    private Currency srcCurrency;
    private Currency tgtCurrency;

    public List<Currency> getSupportedCurrencies() {
        return Arrays.asList(Currency.values());
    }

}
