package com.tripcast.tourweather.tourspot.course.dto;

import java.util.List;

public record TourCourseWeatherResponse(
        Long courseId,
        String sourceCourseId,
        List<TourCourseStopWeatherResponse> stops
) {
}
