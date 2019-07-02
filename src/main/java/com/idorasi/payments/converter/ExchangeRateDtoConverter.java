package com.idorasi.payments.converter;

import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import com.idorasi.payments.model.ExchangeRate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExchangeRateDtoConverter {

    private CurrencyRepository currencyRepository;

    public ExchangeRateDtoConverter(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public ExchangeRateDtoConverter() {

    }

    public List<ExchangeRate> convertToEntity(List<ExchangeRateDto> exchangeDtoList) {
        List<ExchangeRate> exchangeRateList = exchangeDtoList.stream()
                .map(exchangeDto -> convertToExchangeRateList(exchangeDto))
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());

        return exchangeRateList;
    }


    private ExchangeRate convertToExchangeRate(ExchangeRateDto exchangeDto,Map.Entry<String,Double> rate) {
        Currency sourceCurrency = currencyRepository.findBySymbol(exchangeDto.getBase())
                .orElseThrow(IllegalArgumentException::new);
        Currency targetCurrency = currencyRepository.findBySymbol(rate.getKey())
                .orElseThrow(IllegalArgumentException::new);

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSourceCurrency(sourceCurrency.getId());
        exchangeRate.setDate(exchangeDto.getDate());
        exchangeRate.setTargetCurrency(targetCurrency.getId());
        exchangeRate.setRate(rate.getValue());

        return exchangeRate;
    }

    private List<ExchangeRate> convertToExchangeRateList(ExchangeRateDto exchangeDto) {
        return exchangeDto.getRates().entrySet().stream()
                .map(rate -> convertToExchangeRate(exchangeDto,rate))
                .collect(Collectors.toList());
    }




}
