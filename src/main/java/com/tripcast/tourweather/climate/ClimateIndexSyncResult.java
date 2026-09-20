package com.tripcast.tourweather.climate;

public record ClimateIndexSyncResult(
        int requestedRegions,
        int successfulRegions,
        int savedRecords,
        int failedRegions
) {
}
