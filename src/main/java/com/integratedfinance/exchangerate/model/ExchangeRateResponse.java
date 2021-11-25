package com.integratedfinance.exchangerate.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeRateResponse {
	
	private BigDecimal targetRate;
	private Error error;

	
}
