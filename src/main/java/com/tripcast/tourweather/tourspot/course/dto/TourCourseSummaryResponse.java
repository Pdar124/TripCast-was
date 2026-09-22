package com.tripcast.tourweather.tourspot.course.dto;

public record TourCourseSummaryResponse(
        Long courseId,
        String sourceCourseId,
        String representativeSpotName,
        String representativeThemeName,
        long stopCount
) {
}
