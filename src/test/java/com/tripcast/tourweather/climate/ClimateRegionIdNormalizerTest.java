package com.tripcast.tourweather.climate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.tripcast.tourweather.climate.util.ClimateRegionIdNormalizer;

class ClimateRegionIdNormalizerTest {

    @Test
    void 읍면동_코드를_시군구_코드로_정규화한다() {
        assertAll(
                () -> assertEquals(
                        "4822000000",
                        ClimateRegionIdNormalizer.normalize("4822051000")
                ),
                () -> assertEquals(
                        "4822000000",
                        ClimateRegionIdNormalizer.normalize("4822036001")
                ),
                () -> assertEquals(
                        "5013000000",
                        ClimateRegionIdNormalizer.normalize("5013000000")
                )
        );
    }
}
