package com.darae.tourweather.weather.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

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
    public String getVillageForecast(Long courseId) {

    String currentDate = "2019122010";

    return restClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/getTourStnVilageFcst")
                    .queryParam("pageNo", 1)
                    .queryParam("numOfRows", 10)
                    .queryParam("dataType", "JSON")
                    .queryParam("CURRENT_DATE", currentDate)
                    .queryParam("HOUR", 24)
                    .queryParam("COURSE_ID", courseId)
                    .queryParam("authKey", apiKey)
                    .build())
            .retrieve()
            .body(String.class);
}
public String getVillageForecast() {

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
            .body(String.class);
}
}