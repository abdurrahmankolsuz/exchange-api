package com.integratedfinance.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class ExchangeRates {

    private Boolean success;
    private String terms;
    private String privacy;
    private String date;
    private String source;
    private Long timestamp;
    private Error error;
    private Map<String, String> quotes;


}
