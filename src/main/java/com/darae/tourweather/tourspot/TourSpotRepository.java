package com.darae.tourweather.tourspot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSpotRepository
        extends JpaRepository<TourSpot, Long> {

    List<TourSpot> findByNameContaining(String keyword);
}