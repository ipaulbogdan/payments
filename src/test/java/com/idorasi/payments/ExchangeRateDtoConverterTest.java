package com.idorasi.payments;

import com.idorasi.payments.converter.ExchangeRateDtoConverter;
import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.ExchangeRate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExchangeRateDtoConverterTest {

    @Test
    public void emptyListRun(){
        ExchangeRateDtoConverter converter = new ExchangeRateDtoConverter();
        List<ExchangeRateDto> emptyDtos = new ArrayList<>();

        List<ExchangeRate> exchangeRates = converter.convertToEntity(emptyDtos);

        assertEquals(0, exchangeRates.size());
    }
}
