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
import java.time.LocalDate;
import java.util.List;

import static com.idorasi.payments.service.ExchangeRateService.LOGGER;

@Component
public class ExchangeRateClient {

    private static final String BASE_API_URL = "https://api.exchangeratesapi.io/";

    @Retryable(value = {HttpClientErrorException.class}, backoff = @Backoff(200))
    public ExchangeRateDto getRatesFromExternalApi(LocalDate date, String baseSymbol, List<Currency> pairCurrency) {
        String  exchangeRatesApiUrl
                = createExternalApiUri(date, baseSymbol, pairCurrency);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExchangeRateDto> response = restTemplate.exchange(
                exchangeRatesApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ExchangeRateDto>(){});

        return response.getBody();
    }

    private String createExternalApiUri(LocalDate date, String baseSymbol, List<Currency> pairCurrency) {
        try {
            URIBuilder uriBuilder = new URIBuilder(BASE_API_URL + date.toString());
            uriBuilder.addParameter("base", baseSymbol);

            for (Currency currency : pairCurrency){
                uriBuilder.addParameter("symbols", currency.getSymbol());
            }

            return uriBuilder.toString();
        } catch (URISyntaxException e) {
            LOGGER.error("Error creating externalUrl", e);
            throw new IllegalArgumentException();
        }
    }

    @Recover
    public ExchangeRateDto getExternalRecovery(Exception exception) {
        LOGGER.error("Can't access external api",exception);
        return new ExchangeRateDto();
    }






}
