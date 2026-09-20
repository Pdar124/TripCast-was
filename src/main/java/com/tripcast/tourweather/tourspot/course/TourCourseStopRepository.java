package com.tripcast.tourweather.tourspot.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourCourseStopRepository
        extends JpaRepository<TourCourseStop, Long> {

    boolean existsBySourceSpotId(String sourceSpotId);

    List<TourCourseStop> findByCourseIdOrderByCourseOrderAsc(Long courseId);

    Optional<TourCourseStop> findFirstByTourSpotIdOrderByIdAsc(Long tourSpotId);

    @Query("""
            select distinct stop.regionId
            from TourCourseStop stop
            where stop.regionId is not null
              and stop.regionId <> ''
            order by stop.regionId
            """)
    List<String> findDistinctRegionIds();
}
