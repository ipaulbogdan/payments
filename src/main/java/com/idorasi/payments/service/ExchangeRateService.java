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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    public List<ExchangeRate> updateExchangeRates(LocalDate date){
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        List<Currency> currencyList = currencyRepository.findAll();

        for (Currency currency : currencyList){
            List<Currency> pairCurrencyList = buildPairCurrencies(currency,currencyList);

            exchangeRateDtoList.add(
                    exchangeRateClient.getRatesFromExternalApi(
                            date,
                            currency.getSymbol(),
                            pairCurrencyList)
                    );
        }

        ExchangeRateDtoConverter exchangeDtoConverter = new ExchangeRateDtoConverter(currencyRepository);
        return exchangeRateRepository.saveAll(exchangeDtoConverter.convertToEntity(exchangeRateDtoList));
    }


    public List<ExchangeRate> viewAll() {
        return exchangeRateRepository.findAll();
    }


    public Optional<ExchangeRate> findByBaseAndPair(Long id, Long targetCurrencyId) {
       return exchangeRateRepository.findFirstBySourceCurrencyAndTargetCurrencyOrderByDateDesc(id,targetCurrencyId);
    }

    private List<Currency> buildPairCurrencies(Currency currency, List<Currency> baseCurrencyList) {
        List<Currency> pairCurrencies = new ArrayList<>(baseCurrencyList);
        pairCurrencies.remove(currency);

        return pairCurrencies;
    }
}
