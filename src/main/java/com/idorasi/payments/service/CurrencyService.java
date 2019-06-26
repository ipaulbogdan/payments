package com.idorasi.payments.service;

import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency add(Currency currency){
        return currencyRepository.save(currency);
    }

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public Currency findBySymbol(String base) {
        return(currencyRepository.findBySymbol(base));
    }
}
