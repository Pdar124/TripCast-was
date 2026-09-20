package com.tripcast.tourweather.tourspot.importer;

public record TourSpotCsvRow(
        String themeCode,
        String sourceCourseId,
        String sourceSpotId,
        String regionId,
        String name,
        double longitude,
        double latitude,
        int courseOrder,
        int travelTime,
        String indoorType,
        String themeName
) {
}