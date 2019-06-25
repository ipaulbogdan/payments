package com.idorasi.payments.converter;

import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.CurrencyRepository;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.service.CurrencyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExchangeRateDtoConverter {

    private CurrencyService currencyService;

    public ExchangeRateDtoConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public List<ExchangeRate> convert(List<ExchangeRateDto> exchangeDtoList) {
        List<ExchangeRate> exchangeRateList = exchangeDtoList.stream()
                .map(exchangeDto -> convertToExchangeRateList(exchangeDto))
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());

        return exchangeRateList;
    }

    private ExchangeRate convertToExchangeRate(ExchangeRateDto exchangeDto,Map.Entry<String,Double> rate){
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSourceCurrency(currencyService.findBySymbol(exchangeDto.getBase()).getId());
        exchangeRate.setDate(exchangeDto.getDate());
        exchangeRate.setTargetCurrency(currencyService.findBySymbol(rate.getKey()).getId());
        exchangeRate.setRate(rate.getValue());

        return exchangeRate;
    }

    private List<ExchangeRate> convertToExchangeRateList(ExchangeRateDto exchangeDto){
        return exchangeDto.getRates().entrySet().stream()
                .map(rate -> convertToExchangeRate(exchangeDto,rate))
                .collect(Collectors.toList());
    }




}
