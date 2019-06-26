package com.idorasi.payments.service;

import com.idorasi.payments.converter.ExchangeRateDtoConverter;
import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


import java.net.NoRouteToHostException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    public List<ExchangeRate> add(String date) throws URISyntaxException, Exception{
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        List<Currency> currencyList = currencyService.findAll();
        List<Currency> pairCurrencyList;
        String externalURL;

        for(Currency currency: currencyList){
            pairCurrencyList = new ArrayList<>(currencyList);
            pairCurrencyList.remove(currency);

            externalURL = exchangeRateClient.createExternalApiUri(
                    date,
                    currency.getSymbol(),
                    pairCurrencyList);

            exchangeRateDtoList.add(exchangeRateClient
                    .getRatesFromExternalApi(externalURL));
        }
        ExchangeRateDtoConverter exchangeDtoConverter = new ExchangeRateDtoConverter(currencyService);

        return exchangeRateRepository.saveAll(exchangeDtoConverter.convert(exchangeRateDtoList));
    }


    public List<ExchangeRate> viewAll(){
        return exchangeRateRepository.findAll();
    }


    public ExchangeRate findByBaseAndPair(Long id, Long targetCurrencyId) {
       return(exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByDateDesc(id,targetCurrencyId).get(0));
    }
}
