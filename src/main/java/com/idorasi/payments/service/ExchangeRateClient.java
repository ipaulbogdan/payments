package com.idorasi.payments.service;

import com.idorasi.payments.dto.ExchangeRateDto;
import com.idorasi.payments.model.Currency;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class ExchangeRateClient {

    private static final String BASE_API_URL = "https://api.exchangeratesapi.io/";

    String createExternalApiUri(String date, String baseSymbol, List<Currency> pairCurrency) throws URISyntaxException {
        URIBuilder uri = new URIBuilder(BASE_API_URL + date);
        uri.addParameter("base", baseSymbol);

        for (Currency currency : pairCurrency){
            uri.addParameter("symbols", currency.getSymbol());
        }

        return uri.toString();
    }

    @Retryable(value = {HttpClientErrorException.class}, backoff = @Backoff(200))
    public ExchangeRateDto getRatesFromExternalApi(String exchangeRatesApiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExchangeRateDto> response = restTemplate.exchange(
                exchangeRatesApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ExchangeRateDto>(){});

        return response.getBody();
    }

    @Recover
    public ExchangeRateDto getExternalRecovery(Exception exception) {
        ExchangeRateService.LOGGER.error("Can't access external api",exception);
        return new ExchangeRateDto();
    }






}
