package com.integratedfinance.exchangerate.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.integratedfinance.exchangerate.model.*;
import com.integratedfinance.exchangerate.repository.ExchangeRepository;
import com.integratedfinance.exchangerate.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ExchangeController {

    @Autowired
    ExchangeRepository exchangeRepository;

    @Autowired
    ExchangeService exchangeService;

    @GetMapping("/exchange")
    public ResponseEntity<List<ExchangeConversion>> getExchangeByDate(@RequestParam("date")
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<ExchangeConversion> exchangeConversions = new ArrayList<>();

            if (date == null) {
                exchangeConversions.addAll(exchangeRepository.findAll());
            } else
                exchangeConversions.addAll(exchangeRepository.findByConversionDate(date));

            if (exchangeConversions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(exchangeConversions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exchange/{id}")
    public ResponseEntity<ExchangeConversion> getExchangeById(@PathVariable("id") long id) {
        Optional<ExchangeConversion> exchangeConversion = Optional.ofNullable(exchangeRepository.findById(id));
        return exchangeConversion.map(conversion -> new ResponseEntity<>(conversion, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/exchange/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            exchangeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/exchange")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            exchangeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/live")
    public ResponseEntity<ExchangeRateResponse> exchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {

        try {
            ExchangeRateResponse rateResponse = exchangeService.getExchangeRate(exchangeRateRequest.getSrcCurrency(), exchangeRateRequest.getTgtCurrency());
            return new ResponseEntity<>(rateResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/convert")
    public ResponseEntity<ConversionResponse> doConvert(@RequestBody ConversionRequest conversionRequest) {
        try {
            ConversionResponse rm = exchangeService.getConvertedValue(conversionRequest.getSrcCurrency(), conversionRequest.getTgtCurrency(),
                    conversionRequest.getSrcAmount());

            return new ResponseEntity<>(rm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/convert-manual")
    public ResponseEntity<ConversionResponse> doConvertManually(@RequestBody ConversionRequest conversionRequest) {
        try {
            ConversionResponse rm = exchangeService.getManuallyConvertedValue(conversionRequest.getSrcCurrency(), conversionRequest.getTgtCurrency(),
                    conversionRequest.getSrcAmount());

            return new ResponseEntity<>(rm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
