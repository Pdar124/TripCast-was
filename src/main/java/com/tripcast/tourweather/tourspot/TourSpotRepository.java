package com.tripcast.tourweather.tourspot;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSpotRepository
        extends JpaRepository<TourSpot, Long> {

    Page<TourSpot> findByNameContaining(
            String keyword,
            Pageable pageable
    );

    Optional<TourSpot> findByNameAndLatitudeAndLongitude(
            String name,
            Double latitude,
            Double longitude
    );
}