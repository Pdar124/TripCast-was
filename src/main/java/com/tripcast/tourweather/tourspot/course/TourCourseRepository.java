package com.tripcast.tourweather.tourspot.course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourCourseRepository
        extends JpaRepository<TourCourse, Long> {

    Optional<TourCourse> findBySourceCourseId(
            String sourceCourseId
    );
}