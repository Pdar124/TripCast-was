package com.tripcast.tourweather.tourspot.course;

public class TourCourseNotFoundException extends RuntimeException {

    public TourCourseNotFoundException(Long courseId) {
        super("관광코스를 찾을 수 없습니다: " + courseId);
    }
}
