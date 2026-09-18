package com.darae.tourweather.tourspot;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSpotRepository
        extends JpaRepository<TourSpot, Long> {

    List<TourSpot> findByNameContaining(String keyword);

    Optional<TourSpot> findByNameAndLatitudeAndLongitude(
            String name,
            Double latitude,
            Double longitude
    );
}