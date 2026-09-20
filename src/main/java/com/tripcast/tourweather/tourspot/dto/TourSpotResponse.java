package com.tripcast.tourweather.tourspot.dto;

import com.tripcast.tourweather.tourspot.TourSpot;

public record TourSpotResponse(
        Long id,
        String name,
        Double latitude,
        Double longitude
) {
    public static TourSpotResponse from(TourSpot tourSpot) {
        return new TourSpotResponse(
                tourSpot.getId(),
                tourSpot.getName(),
                tourSpot.getLatitude(),
                tourSpot.getLongitude()
        );
    }
}