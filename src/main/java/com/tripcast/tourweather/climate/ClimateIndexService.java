package com.tripcast.tourweather.climate;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.climate.repository.RegionClimateIndexRepository;
import com.tripcast.tourweather.climate.util.ClimateRegionIdNormalizer;

@Service
@Transactional(readOnly = true)
public class ClimateIndexService {

    private final RegionClimateIndexRepository climateIndexRepository;

    public ClimateIndexService(
            RegionClimateIndexRepository climateIndexRepository
    ) {
        this.climateIndexRepository = climateIndexRepository;
    }

    public ClimateIndexResponse getLatest(String regionId) {
        return findLatest(ClimateRegionIdNormalizer.normalize(regionId));
    }

    public ClimateIndexResponse findLatestOrNull(String regionId) {
        String normalizedRegionId = ClimateRegionIdNormalizer.normalize(
                regionId
        );
        return climateIndexRepository
                .findFirstByRegionIdOrderByBaseDateDesc(normalizedRegionId)
                .map(ClimateIndexResponse::from)
                .orElse(null);
    }

    public List<ClimateIndexResponse> getRange(
            String regionId,
            LocalDate from,
            LocalDate to
    ) {
        if (from.isAfter(to)) {
            throw new InvalidClimateIndexRangeException();
        }

        String normalizedRegionId = ClimateRegionIdNormalizer.normalize(
                regionId
        );
        if (!climateIndexRepository.existsByRegionId(normalizedRegionId)) {
            throw new RegionClimateIndexNotFoundException(regionId);
        }

        return climateIndexRepository
                .findByRegionIdAndBaseDateBetweenOrderByBaseDateAsc(
                        normalizedRegionId,
                        from,
                        to
                )
                .stream()
                .map(ClimateIndexResponse::from)
                .toList();
    }

    private ClimateIndexResponse findLatest(String regionId) {
        return climateIndexRepository
                .findFirstByRegionIdOrderByBaseDateDesc(regionId)
                .map(ClimateIndexResponse::from)
                .orElseThrow(() ->
                        new RegionClimateIndexNotFoundException(regionId));
    }
}
