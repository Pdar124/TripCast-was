package com.darae.tourweather.weather.client;

import com.darae.tourweather.weather.dto.KmaWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KmaWeatherClient {

    private final RestClient restClient;
    private final String apiKey;

    public KmaWeatherClient(
            RestClient.Builder restClientBuilder,
            @Value("${weather.api.base-url}") String baseUrl,
            @Value("${weather.api.key}") String apiKey
    ) {

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();

        this.apiKey = apiKey;
    }

    public KmaWeatherResponse getVillageForecast() {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getVilageFcst")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 1000)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", "20260917")
                        .queryParam("base_time", "2000")
                        .queryParam("nx", 55)
                        .queryParam("ny", 127)
                        .queryParam("authKey", apiKey)
                        .build())
                .retrieve()
                .body(KmaWeatherResponse.class);
    }
}