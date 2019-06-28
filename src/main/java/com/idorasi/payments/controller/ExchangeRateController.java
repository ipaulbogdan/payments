package com.idorasi.payments.controller;

import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//todo thymeleaf for convert view
@RestController
@RequestMapping("/api/exchanges")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRatesService;


    @PostMapping("/exchange-rate-date/{exchangeRateDate}")
    public List<ExchangeRate> addExchangeRates(@PathVariable String exchangeRateDate) {
        return(exchangeRatesService.updateExchangeRates(exchangeRateDate));
    }

    @GetMapping
    public List<ExchangeRate> viewAllExchangeRates() {
        return(exchangeRatesService.viewAll());
    }






}


