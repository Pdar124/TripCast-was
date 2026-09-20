package com.tripcast.tourweather.climate.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripcast.tourweather.climate.domain.RegionClimateIndex;

public interface RegionClimateIndexRepository
        extends JpaRepository<RegionClimateIndex, Long> {

    Optional<RegionClimateIndex> findByRegionIdAndBaseDate(
            String regionId,
            LocalDate baseDate
    );

    Optional<RegionClimateIndex> findFirstByRegionIdOrderByBaseDateDesc(
            String regionId
    );

    List<RegionClimateIndex> findByRegionIdAndBaseDateBetweenOrderByBaseDateAsc(
            String regionId,
            LocalDate from,
            LocalDate to
    );
}
