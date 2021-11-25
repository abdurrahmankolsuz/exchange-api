package com.integratedfinance.exchangerate.repository;

import com.integratedfinance.exchangerate.model.ExchangeConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<ExchangeConversion, Long> {

    ExchangeConversion findById(long id);

    List<ExchangeConversion> findByConversionDate(LocalDate date);
}
