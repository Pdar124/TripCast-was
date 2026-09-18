package com.darae.tourweather.common.error;

public record ErrorResponse(
        int status,
        String message
) {
}