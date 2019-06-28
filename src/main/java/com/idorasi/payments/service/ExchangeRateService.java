package com.idorasi.payments.service;

import com.idorasi.payments.converter.ExchangeRateDtoConverter;
import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    public List<ExchangeRate> updateExchangeRates(String date){
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        List<Currency> currencyList = currencyRepository.findAll();

        for (Currency currency : currencyList){
            List<Currency> pairCurrencyList = buildPairCurrencies(currency,currencyList);

            try {
                String externalUrl = exchangeRateClient.createExternalApiUri(
                        date,
                        currency.getSymbol(),
                        pairCurrencyList);

                exchangeRateDtoList.add(exchangeRateClient.getRatesFromExternalApi(externalUrl));
            } catch (URISyntaxException e) {
                LOGGER.error("Error creating externalUrl", e);
            }
        }

        ExchangeRateDtoConverter exchangeDtoConverter = new ExchangeRateDtoConverter(currencyRepository);
        return exchangeRateRepository.saveAll(exchangeDtoConverter.convertToEntity(exchangeRateDtoList));
    }


    public List<ExchangeRate> viewAll() {
        return exchangeRateRepository.findAll();
    }


    public ExchangeRate findByBaseAndPair(Long id, Long targetCurrencyId) {
       return(exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByDateDesc(id,targetCurrencyId).get().get(0));
    }

    private List<Currency> buildPairCurrencies(Currency currency, List<Currency> baseCurrencyList) {
        List<Currency> pairCurrencies = new ArrayList<>(baseCurrencyList);
        pairCurrencies.remove(currency);

        return pairCurrencies;
    }
}
