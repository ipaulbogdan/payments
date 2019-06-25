package com.idorasi.payments.controller;


import com.idorasi.payments.model.Currency;
import com.idorasi.payments.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @PostMapping("/add")
    public Currency addNewCurrency(@RequestBody Currency currency){
        return(currencyService.add(currency));
    }

    @GetMapping("/get/all")
    public List<Currency> getAll(){
        return currencyService.findAll();
    }

    @GetMapping("/find")
    public Object findById(@RequestParam String symbol){
        return currencyService.findBySymbol(symbol);
    }

}
