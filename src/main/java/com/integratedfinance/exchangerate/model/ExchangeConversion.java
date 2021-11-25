package com.integratedfinance.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integratedfinance.exchangerate.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchange_conversion")
public class ExchangeConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "converted_value")
    private BigDecimal convertedValue;

    @Column(name = "conversion_date")
    private LocalDate conversionDate;

    @Column(name = "source_currency")
    private Currency srcCurrency;

    @Column(name = "target_currency")
    private Currency tgtCurrency;

    @Column(name = "amount")
    private BigDecimal amount;


}
