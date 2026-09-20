package com.tripcast.tourweather.tourspot.dto;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.weather.dto.WeatherResponse;

public record TourSpotWeatherResponse(
        Long tourSpotId,
        String tourSpotName,
        Double latitude,
        Double longitude,
        WeatherResponse weather,
        ClimateIndexResponse climateIndex
) {
}
