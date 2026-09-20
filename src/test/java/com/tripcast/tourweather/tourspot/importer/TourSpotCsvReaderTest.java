package com.tripcast.tourweather.tourspot.importer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TourSpotCsvReaderTest {

    private final TourSpotCsvReader csvReader =
            new TourSpotCsvReader();

    @Test
    void 관광지_csv를_읽는다() {
        List<TourSpotCsvRow> rows = csvReader.read();

        assertEquals(2785, rows.size());

        TourSpotCsvRow firstRow = rows.get(0);

        assertAll(
                () -> assertEquals("TH05", firstRow.themeCode()),
                () -> assertEquals("177", firstRow.sourceCourseId()),
                () -> assertEquals("17703", firstRow.sourceSpotId()),
                () -> assertEquals(
                        "(통영)세병관(통제영지)",
                        firstRow.name()
                ),
                () -> assertEquals(
                        128.423238,
                        firstRow.longitude()
                ),
                () -> assertEquals(
                        34.847749,
                        firstRow.latitude()
                ),
                () -> assertEquals("실외", firstRow.indoorType())
        );
    }
}