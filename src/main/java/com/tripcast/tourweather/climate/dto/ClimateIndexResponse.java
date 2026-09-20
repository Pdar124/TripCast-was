package com.tripcast.tourweather.climate.dto;

import java.time.LocalDate;

import com.tripcast.tourweather.climate.domain.RegionClimateIndex;

public record ClimateIndexResponse(
        String regionId,
        LocalDate date,
        int score,
        String grade
) {
    public static ClimateIndexResponse from(RegionClimateIndex climateIndex) {
        return new ClimateIndexResponse(
                climateIndex.getRegionId(),
                climateIndex.getBaseDate(),
                climateIndex.getScore(),
                climateIndex.getGrade()
        );
    }
}
