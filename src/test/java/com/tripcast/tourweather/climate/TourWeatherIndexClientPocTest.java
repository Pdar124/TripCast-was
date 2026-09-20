package com.tripcast.tourweather.climate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.web.client.RestClient;

import com.tripcast.tourweather.climate.client.TourWeatherIndexClient;
import com.tripcast.tourweather.climate.client.dto.TourClimateApiResponse.Item;

class TourWeatherIndexClientPocTest {

    @Test
    @EnabledIfEnvironmentVariable(
            named = "TOUR_CLIMATE_API_KEY",
            matches = ".+"
    )
    void 실제_관광기후지수_API_응답_필드를_확인한다() {
        TourWeatherIndexClient client = new TourWeatherIndexClient(
                RestClient.builder(),
                "https://apis.data.go.kr/1360000/TourStnInfoService1",
                System.getenv("TOUR_CLIMATE_API_KEY"),
                Duration.ofSeconds(3),
                Duration.ofSeconds(10)
        );

        LocalDateTime requestTime = LocalDateTime
                .now(ZoneId.of("Asia/Seoul"))
                .truncatedTo(ChronoUnit.HOURS);
        List<Item> items = client.getCityTourClimateIndex(
                "5013000000",
                requestTime,
                1
        );

        assertFalse(items.isEmpty());
        Item item = items.getFirst();
        assertAll(
                () -> assertTrue(item.tm() != null && !item.tm().isBlank()),
                () -> assertTrue(
                        item.cityAreaId() != null
                                && !item.cityAreaId().isBlank()
                ),
                () -> assertDoesNotThrow(
                        () -> new BigDecimal(item.kmaTci())
                ),
                () -> assertTrue(
                        item.tciGrade() != null
                                && !item.tciGrade().isBlank()
                )
        );
        System.out.printf(
                "tour-climate-poc tm=%s cityAreaId=%s kmaTci=%s tciGrade=%s%n",
                item.tm(),
                item.cityAreaId(),
                item.kmaTci(),
                item.tciGrade()
        );
    }
}
