package com.idorasi.payments;

import com.idorasi.payments.converter.ExchangeRateDtoConverter;
import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import com.idorasi.payments.model.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateDtoConverterTest {

    @Mock
    CurrencyRepository currencyRepository;

    @Test
    public void whenEmptyDtoList_emptyResult() {
        ExchangeRateDtoConverter converter = new ExchangeRateDtoConverter(currencyRepository);
        List<ExchangeRateDto> emptyDtos = new ArrayList<>();

        List<ExchangeRate> exchangeRates = converter.convertToEntity(emptyDtos);

        assertEquals(0, exchangeRates.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenDtoListHasUnknownBase_EntityNotFoundException() {
        ExchangeRateDtoConverter converter = new ExchangeRateDtoConverter(currencyRepository);
        List<ExchangeRateDto> exchangeRateDtos = new ArrayList<>();
        HashMap<String,Double> rates = new HashMap<>();
        rates.put("USD",4.88);
        rates.put("RON",3.5);
        rates.put("EUR",4.75);
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("UNKNOWN");
        exchangeRateDto.setRates(rates);
        exchangeRateDtos.add(exchangeRateDto);
        converter.convertToEntity(exchangeRateDtos);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenDtoListHasUnknownPair_EntityNotFoundException() {
        ExchangeRateDtoConverter converter = new ExchangeRateDtoConverter(currencyRepository);
        List<ExchangeRateDto> exchangeRateDtos = new ArrayList<>();
        HashMap<String,Double> rates = new HashMap<>();
        rates.put("UNKNOWN",4.88);
        rates.put("RON",3.5);
        rates.put("EUR",4.75);
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setRates(rates);
        exchangeRateDtos.add(exchangeRateDto);
        Currency dummyCurrency = new Currency();
        dummyCurrency.setId((long) 2.0);
        dummyCurrency.setName("dolar");
        dummyCurrency.setSymbol("USD");

        when(currencyRepository.findBySymbol("USD")).thenReturn(Optional.of(dummyCurrency));
        converter.convertToEntity(exchangeRateDtos);
    }
}
