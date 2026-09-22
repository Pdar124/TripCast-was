package com.tripcast.tourweather.tourspot.course.dto;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;

public record CourseRecommendationResponse(
        Long courseId,
        String sourceCourseId,
        ClimateIndexResponse climateIndex
) {
}
