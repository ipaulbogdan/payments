package com.idorasi.payments.controller;


import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRatesService;


    @PostMapping("/add/{date}")
    public List<ExchangeRate> addExchangeRates(@PathVariable String date) throws URISyntaxException {
        return(exchangeRatesService.add(date));
    }

    @GetMapping("/view/all")
    public List<ExchangeRate> viewAllExchangeRates(){
        return(exchangeRatesService.viewAll());
    }






}

