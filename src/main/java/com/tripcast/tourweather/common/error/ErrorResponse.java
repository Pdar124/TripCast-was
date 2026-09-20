package com.tripcast.tourweather.common.error;

public record ErrorResponse(
        int status,
        String message
) {
}