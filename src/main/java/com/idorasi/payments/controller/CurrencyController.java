package com.idorasi.payments.controller;


import com.idorasi.payments.model.Currency;
import com.idorasi.payments.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @PostMapping
    public Currency addNewCurrency(@RequestBody Currency currency) {
        return(currencyService.add(currency));
    }

    @GetMapping
    public List<Currency> getAll() {
        return currencyService.findAll();
    }

    @GetMapping("/currency-symbol/{currencySymbol}")
    public Currency findBySymbol(@PathVariable String currencySymbol) {
        return currencyService.findBySymbol(currencySymbol);
    }

}
