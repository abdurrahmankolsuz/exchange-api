package com.integratedfinance.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
public class Error {
	
	private String code;
	private String type;
	private String info;
	
}
