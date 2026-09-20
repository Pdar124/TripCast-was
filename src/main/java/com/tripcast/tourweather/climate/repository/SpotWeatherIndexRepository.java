package com.tripcast.tourweather.climate.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripcast.tourweather.climate.domain.SpotWeatherIndex;

public interface SpotWeatherIndexRepository
        extends JpaRepository<SpotWeatherIndex, Long> {

    Optional<SpotWeatherIndex> findBySourceSpotIdAndBaseDate(
            String sourceSpotId,
            LocalDate baseDate
    );
}
