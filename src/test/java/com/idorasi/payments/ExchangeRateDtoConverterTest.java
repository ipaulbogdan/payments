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
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void hasDtoList_EntityAnswer() {
        ExchangeRateDtoConverter converter = new ExchangeRateDtoConverter(currencyRepository);
        List<ExchangeRateDto> exchangeRateDtos = new ArrayList<>();
        List<ExchangeRate> expectedExchangeRates = new ArrayList<>();
        HashMap<String,Double> rates = new HashMap<>();
        rates.put("CAD",4.88);
        rates.put("RON",3.5);
        rates.put("EUR",4.75);
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setRates(rates);
        exchangeRateDto.setDate(LocalDate.now());
        exchangeRateDtos.add(exchangeRateDto);
        Currency cad = new Currency();
        cad.setSymbol("CAD");
        cad.setName("Canadian");
        cad.setId((long) 1);
        Currency ron = new Currency();
        ron.setSymbol("RON");
        ron.setName("Leu");
        ron.setId((long) 2);
        Currency euro = new Currency();
        euro.setSymbol("EUR");
        euro.setName("Euro");
        euro.setId((long) 3);
        Currency dolar = new Currency();
        dolar.setSymbol("USD");
        dolar.setName("dolar");
        dolar.setId((long) 4);

        when(currencyRepository.findBySymbol("USD")).thenReturn(Optional.of(dolar));
        when(currencyRepository.findBySymbol("CAD")).thenReturn(Optional.of(cad));
        when(currencyRepository.findBySymbol("RON")).thenReturn(Optional.of(ron));
        when(currencyRepository.findBySymbol("EUR")).thenReturn(Optional.of(euro));

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setSourceCurrency(dolar.getId());
        exchangeRate.setTargetCurrency(cad.getId());
        exchangeRate.setRate(4.88);
        expectedExchangeRates.add(exchangeRate);
        exchangeRate = new ExchangeRate();
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setSourceCurrency(dolar.getId());
        exchangeRate.setTargetCurrency(ron.getId());
        exchangeRate.setRate(3.5);
        expectedExchangeRates.add(exchangeRate);
        exchangeRate = new ExchangeRate();
        exchangeRate.setDate(LocalDate.now());
        exchangeRate.setSourceCurrency(dolar.getId());
        exchangeRate.setTargetCurrency(euro.getId());
        exchangeRate.setRate(4.75);
        expectedExchangeRates.add(exchangeRate);
        List<ExchangeRate> exchangeRates = converter.convertToEntity(exchangeRateDtos);

        assertTrue(expectedExchangeRates.containsAll(exchangeRates));
    }
}
