package com.nextbuy.payments.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbuy.payments.config.HttpClientConfig;
import com.nextbuy.payments.controller.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.time.Duration.ofSeconds;
import static org.springframework.http.HttpHeaders.ACCEPT;

@Slf4j
@Component
public record CartGateway(
        ObjectMapper mapper,
        HttpClientConfig httpClientConfig
) {

    private static final String URL = "http://localhost:8082/api/cart?id=%s";
    private static final Duration TIME_OUT = ofSeconds(10);
    private static final String FAIL_DEPENDENCY_ITEMS_MESSAGE = "error to get data from items";

    public Optional<CartDTO> getCart(Long idCart) {
        if (idCart == null) {
            throw new RuntimeException("Id cart can't be null");
        }

        var uri = URI.create(format(URL, idCart));
        var response = sendRequest(uri);

        return Optional.ofNullable(new ObjectMapper().convertValue(response.body(), CartDTO.class));
    }

    private HttpResponse<String> sendRequest(URI uri) {
        try {
            return httpClientConfig
                    .getHttpClient()
                    .build()
                    .sendAsync(getHttpRequest(uri), ofString())
                    .join();
        } catch (Exception ex) {
            log.error(FAIL_DEPENDENCY_ITEMS_MESSAGE, ex);
            throw new RuntimeException(FAIL_DEPENDENCY_ITEMS_MESSAGE, ex);
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
