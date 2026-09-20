package com.tripcast.tourweather.climate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.climate.repository.RegionClimateIndexRepository;

@ExtendWith(MockitoExtension.class)
class ClimateIndexServiceTest {

    @Mock
    private RegionClimateIndexRepository climateIndexRepository;

    @InjectMocks
    private ClimateIndexService climateIndexService;

    @Test
    void 지역은_있지만_기간_데이터가_없으면_빈_목록을_반환한다() {
        LocalDate from = LocalDate.of(2026, 9, 1);
        LocalDate to = LocalDate.of(2026, 9, 2);
        when(climateIndexRepository.existsByRegionId("4822000000"))
                .thenReturn(true);
        when(climateIndexRepository
                .findByRegionIdAndBaseDateBetweenOrderByBaseDateAsc(
                        "4822000000",
                        from,
                        to
                ))
                .thenReturn(List.of());

        List<ClimateIndexResponse> result = climateIndexService.getRange(
                "4822051000",
                from,
                to
        );

        assertTrue(result.isEmpty());
    }
}
