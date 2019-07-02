package com.idorasi.payments;

import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.CurrencyRepository;
import com.idorasi.payments.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;


    @Test
    public void addNewCurrency_Test() {
        //given currency

        Currency currency = new Currency();
        currency.setName("Leu");
        currency.setSymbol("RON");

        doReturn(currency).when(currencyRepository).save(currency);

        Currency returnedCurrency = currencyRepository.save(currency);

        assertThat(returnedCurrency).isEqualTo(currency);
    }

    @Test
    public void findAllCurrencies_Test() {
        List<Currency> currencyList = new ArrayList<>();
        Currency c1 = new Currency();
        Currency c2 = new Currency();
        c1.setName("Leu");
        c1.setSymbol("RON");
        c2.setName("Euro");
        c2.setSymbol("EUR");
        currencyList.add(c1);
        currencyList.add(c2);

        doReturn(currencyList).when(currencyRepository).findAll();
        List<Currency> returnList = currencyRepository.findAll();
        assertThat(returnList).isEqualTo(currencyList);

    }




}
