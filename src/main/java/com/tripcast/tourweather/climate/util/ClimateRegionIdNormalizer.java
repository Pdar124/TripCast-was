package com.tripcast.tourweather.climate.util;

public final class ClimateRegionIdNormalizer {

    private static final int ADMINISTRATIVE_CODE_LENGTH = 10;
    private static final int CITY_DISTRICT_PREFIX_LENGTH = 5;

    private ClimateRegionIdNormalizer() {
    }

    public static String normalize(String regionId) {
        if (regionId == null) {
            return null;
        }

        String value = regionId.trim();
        if (value.length() != ADMINISTRATIVE_CODE_LENGTH
                || !value.chars().allMatch(Character::isDigit)) {
            return value;
        }

        return value.substring(0, CITY_DISTRICT_PREFIX_LENGTH) + "00000";
    }
}
