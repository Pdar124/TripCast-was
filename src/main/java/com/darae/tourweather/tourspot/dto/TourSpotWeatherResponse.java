package com.darae.tourweather.tourspot.dto;

import com.darae.tourweather.weather.dto.WeatherResponse;

public record TourSpotWeatherResponse(
        Long tourSpotId,
        String tourSpotName,
        Double latitude,
        Double longitude,
        WeatherResponse weather
) {
}