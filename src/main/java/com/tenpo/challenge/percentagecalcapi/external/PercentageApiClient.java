package com.tenpo.challenge.percentagecalcapi.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PercentageApiClient {

    private final RestTemplate restTemplate;
    private static final String EXTERNAL_PERCENTAGE_URL = "http://localhost:8080/mock/percentage";

    public Double getPercentageFromMock() {
        return restTemplate.getForObject(EXTERNAL_PERCENTAGE_URL, Double.class);
    }
}
