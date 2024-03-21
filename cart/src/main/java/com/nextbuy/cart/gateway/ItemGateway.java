package com.nextbuy.cart.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.time.Duration.ofSeconds;
import static org.springframework.http.HttpHeaders.ACCEPT;

public record ItemGateway(
        ObjectMapper mapper,
        HttpClientConfig httpClientConfig
) {
    private static final String URL = "https://viacep.com.br/ws/%s/json";
    private static final Duration TIME_OUT = ofSeconds(10);
    private static final String ERROR_VIA_CEP = "error";
    private static final String FAIL_DEPENDENCY_VIA_CEP_MESSAGE = "error to get data from viaCep";
    private static final String NOT_FOUND_ERROR = "Zip code was not found in the database," +
            " Please use a valid postal code or register manually via the route /address";

    public ViaCepDTO getCep(String cep) {
        var uri = URI.create(format(URL, cep));
        var response = sendRequest(uri);
        validateInvalidZipcode(response.body());

        return convertStringToObject(response.body(), ViaCepDTO.class);
    }

    private HttpResponse<String> sendRequest(URI uri) {
        try {
            return httpClientConfig
                    .getHttpClient()
                    .build()
                    .sendAsync(getHttpRequest(uri), ofString())
                    .join();
        } catch (Exception ex) {
            log.error(FAIL_DEPENDENCY_VIA_CEP_MESSAGE, ex);
            throw new FailedDependencyException(FAIL_DEPENDENCY_VIA_CEP_MESSAGE, ex);
        }
    }

    private void validateInvalidZipcode(String body) {
        if (body.contains(ERROR_VIA_CEP)) {
            throw new NotFoundException(NOT_FOUND_ERROR);
        }
    }

    private HttpRequest getHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(TIME_OUT)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}