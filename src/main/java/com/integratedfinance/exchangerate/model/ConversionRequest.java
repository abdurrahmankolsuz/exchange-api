package com.integratedfinance.exchangerate.model;

import com.integratedfinance.exchangerate.util.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ConversionRequest {

    private Currency srcCurrency;

    private Currency tgtCurrency;

    private BigDecimal srcAmount;


    public List<Currency> getSupportedCurrencies() {
        return Arrays.asList(Currency.values());
    }

}
