package com.tripcast.tourweather.tourspot.course.dto;

import java.util.List;

public record TourCourseResponse(
        Long courseId,
        String sourceCourseId,
        List<TourCourseStopResponse> stops
) {
}
