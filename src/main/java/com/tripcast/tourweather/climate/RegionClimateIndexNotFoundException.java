package com.tripcast.tourweather.climate;

public class RegionClimateIndexNotFoundException extends RuntimeException {

    public RegionClimateIndexNotFoundException(String regionId) {
        super("관광기후지수를 찾을 수 없습니다: " + regionId);
    }
}
