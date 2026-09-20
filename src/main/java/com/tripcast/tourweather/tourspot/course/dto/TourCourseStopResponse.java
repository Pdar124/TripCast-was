package com.tripcast.tourweather.tourspot.course.dto;

import com.tripcast.tourweather.tourspot.TourSpot;
import com.tripcast.tourweather.tourspot.course.TourCourseStop;

public record TourCourseStopResponse(
        Long stopId,
        String sourceSpotId,
        Long tourSpotId,
        String tourSpotName,
        Double latitude,
        Double longitude,
        String regionId,
        int courseOrder,
        int travelTime,
        String indoorType,
        String themeCode,
        String themeName
) {
    public static TourCourseStopResponse from(TourCourseStop stop) {
        TourSpot tourSpot = stop.getTourSpot();
        return new TourCourseStopResponse(
                stop.getId(),
                stop.getSourceSpotId(),
                tourSpot.getId(),
                tourSpot.getName(),
                tourSpot.getLatitude(),
                tourSpot.getLongitude(),
                stop.getRegionId(),
                stop.getCourseOrder(),
                stop.getTravelTime(),
                stop.getIndoorType(),
                stop.getThemeCode(),
                stop.getThemeName()
        );
    }
}
