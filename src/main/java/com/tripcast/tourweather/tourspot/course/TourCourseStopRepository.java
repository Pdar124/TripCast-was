package com.tripcast.tourweather.tourspot.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourCourseStopRepository
        extends JpaRepository<TourCourseStop, Long> {

    boolean existsBySourceSpotId(String sourceSpotId);
}