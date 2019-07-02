package com.idorasi.payments.controller;

import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRatesService;


    @PostMapping("/date/{date}")
    public List<ExchangeRate> addExchangeRates(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return(exchangeRatesService.updateExchangeRates(date));
    }

    @PostMapping("/latest")
    public List<ExchangeRate> addLatestExchangeRate() {
        return(exchangeRatesService.updateExchangeRates(LocalDate.now()));
    }

    @GetMapping
    public List<ExchangeRate> viewAllExchangeRates() {
        return(exchangeRatesService.viewAll());
    }






}


