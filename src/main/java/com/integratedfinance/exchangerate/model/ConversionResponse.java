package com.integratedfinance.exchangerate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter @Getter
public class ConversionResponse {
	
	private BigDecimal convertedValue;
	private Long transactionId;
	private Error error;

	
}
