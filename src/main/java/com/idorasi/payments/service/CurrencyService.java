package com.idorasi.payments.service;

import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency add(Currency currency) {
        return currencyRepository.save(currency);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findBySymbol(String base) {
        //todo add spring api error handling
        return currencyRepository.findBySymbol(base)
                .orElseThrow(EntityNotFoundException::new);
    }
}
