package com.darae.tourweather.weather.dto;

public record WeatherResponse(
        String forecastDate,
        String forecastTime,
        double temperature,
        int rainProbability,
        String sky
) {
}