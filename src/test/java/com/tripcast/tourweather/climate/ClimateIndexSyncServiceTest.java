package com.tripcast.tourweather.climate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tripcast.tourweather.climate.client.TourWeatherIndexClient;
import com.tripcast.tourweather.climate.client.dto.TourClimateApiResponse.Item;
import com.tripcast.tourweather.climate.domain.RegionClimateIndex;
import com.tripcast.tourweather.climate.repository.RegionClimateIndexRepository;
import com.tripcast.tourweather.tourspot.course.TourCourseStopRepository;

@ExtendWith(MockitoExtension.class)
class ClimateIndexSyncServiceTest {

    @Mock
    private TourCourseStopRepository courseStopRepository;

    @Mock
    private RegionClimateIndexRepository climateIndexRepository;

    @Mock
    private TourWeatherIndexClient tourWeatherIndexClient;

    @InjectMocks
    private ClimateIndexSyncService climateIndexSyncService;

    @Test
    void 지역별_지수를_저장하고_일부_실패해도_계속한다() {
        LocalDateTime requestTime = LocalDateTime.of(2026, 9, 20, 5, 0);
        Item item = new Item(
                "2026-09-20 06:00",
                "1111000000",
                "서울 종로구",
                "서울",
                "종로구",
                "52",
                "0"
        );

        when(courseStopRepository.findDistinctRegionIds())
                .thenReturn(List.of("1111000000", "2611000000"));
        when(tourWeatherIndexClient.getCityTourClimateIndex(
                "1111000000",
                requestTime,
                1
        )).thenReturn(List.of(item));
        when(tourWeatherIndexClient.getCityTourClimateIndex(
                "2611000000",
                requestTime,
                1
        )).thenThrow(new IllegalStateException("외부 API 실패"));
        when(climateIndexRepository.findByRegionIdAndBaseDate(
                "1111000000",
                LocalDate.of(2026, 9, 20)
        )).thenReturn(Optional.empty());

        ClimateIndexSyncResult result = climateIndexSyncService.sync(
                requestTime,
                1
        );

        ArgumentCaptor<RegionClimateIndex> captor =
                ArgumentCaptor.forClass(RegionClimateIndex.class);
        verify(climateIndexRepository).save(captor.capture());
        RegionClimateIndex saved = captor.getValue();

        assertAll(
                () -> assertEquals(2, result.requestedRegions()),
                () -> assertEquals(1, result.successfulRegions()),
                () -> assertEquals(1, result.savedRecords()),
                () -> assertEquals(1, result.failedRegions()),
                () -> assertEquals("1111000000", saved.getRegionId()),
                () -> assertEquals(LocalDate.of(2026, 9, 20), saved.getBaseDate()),
                () -> assertEquals(52, saved.getScore()),
                () -> assertEquals("0", saved.getGrade())
        );
    }

    @Test
    void 같은_지역과_날짜는_기존_행을_갱신한다() {
        LocalDateTime requestTime = LocalDateTime.of(2026, 9, 20, 5, 0);
        LocalDate baseDate = LocalDate.of(2026, 9, 20);
        RegionClimateIndex existing = new RegionClimateIndex(
                "1111000000",
                baseDate,
                40,
                "기존"
        );
        Item item = new Item(
                "202609200600",
                "1111000000",
                "서울 종로구",
                "서울",
                "종로구",
                "61.4",
                "좋음"
        );

        when(courseStopRepository.findDistinctRegionIds())
                .thenReturn(List.of("1111000000"));
        when(tourWeatherIndexClient.getCityTourClimateIndex(
                eq("1111000000"),
                eq(requestTime),
                eq(1)
        )).thenReturn(List.of(item));
        when(climateIndexRepository.findByRegionIdAndBaseDate(
                "1111000000",
                baseDate
        )).thenReturn(Optional.of(existing));

        climateIndexSyncService.sync(requestTime, 1);

        ArgumentCaptor<RegionClimateIndex> captor =
                ArgumentCaptor.forClass(RegionClimateIndex.class);
        verify(climateIndexRepository).save(captor.capture());

        assertAll(
                () -> assertSame(existing, captor.getValue()),
                () -> assertEquals(61, existing.getScore()),
                () -> assertEquals("좋음", existing.getGrade())
        );
    }
}
