package com.idorasi.payments.service;


import com.idorasi.payments.converter.ExchangeRateDtoConverter;
import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.ExchangeRateRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private CurrencyService currencyService;

    private static final String BASE_API_URL = "https://api.exchangeratesapi.io/";


    public List<ExchangeRate> add(String date) throws URISyntaxException {
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        List<Currency> currencyList = currencyService.findAll();
        List<Currency> pairCurrencyList;

        for(Currency currency: currencyList){
            pairCurrencyList = new ArrayList<>(currencyList);
            pairCurrencyList.remove(currency);


            exchangeRateDtoList.add(
                    getRatesFromExternalApi(
                    createExternalApiUri(
                            date,
                            currency.getSymbol(),
                            pairCurrencyList)
                    )
            );
        }
        ExchangeRateDtoConverter exchangeDtoConverter = new ExchangeRateDtoConverter(currencyService);

        return exchangeRateRepository.saveAll(exchangeDtoConverter.convert(exchangeRateDtoList));
    }

    private String createExternalApiUri(String date,String baseSymbol,List<Currency> pairCurrency) throws URISyntaxException {

        URIBuilder uri = new URIBuilder(BASE_API_URL+date);
        uri.addParameter("base",baseSymbol);

        for(Currency currency:pairCurrency){
            uri.addParameter("symbols",currency.getSymbol());
        }

        return uri.toString();
    }

    private ExchangeRateDto getRatesFromExternalApi(String exchangeRatesApiUrl){

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ExchangeRateDto> response = restTemplate.exchange(
                exchangeRatesApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ExchangeRateDto>(){});


        return response.getBody();
    }


    public List<ExchangeRate> viewAll(){
        return exchangeRateRepository.findAll();
    }




}
