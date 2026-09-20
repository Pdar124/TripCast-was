package com.tripcast.tourweather.tourspot.course.dto;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.weather.dto.WeatherResponse;

public record TourCourseStopWeatherResponse(
        TourCourseStopResponse spot,
        ClimateIndexResponse climateIndex,
        WeatherResponse weather
) {
}
