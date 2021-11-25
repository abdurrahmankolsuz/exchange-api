package com.integratedfinance.exchangerate.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class ConversionRates {

    private Boolean success;
    private String terms;
    private String privacy;
    private Long timestamp;
    private Error error;
    private String result;
    private String query;
    private String info;
    private Map<String, String> quotes;

}
