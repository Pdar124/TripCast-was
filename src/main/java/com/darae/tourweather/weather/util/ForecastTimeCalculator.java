package com.darae.tourweather.weather.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ForecastTimeCalculator {

    private static final int[] BASE_HOURS = {
            2, 5, 8, 11, 14, 17, 20, 23
    };

    public static ForecastTime calculate() {

        // 발표 직후 데이터가 아직 준비되지 않을 수 있어서
        // 10분 전 시각을 기준으로 계산
        LocalDateTime now = LocalDateTime
                .now(ZoneId.of("Asia/Seoul"))
                .minusMinutes(10);

        LocalDate baseDate = now.toLocalDate();
        int currentHour = now.getHour();

        int baseHour = -1;

        for (int hour : BASE_HOURS) {
            if (hour <= currentHour) {
                baseHour = hour;
            }
        }

        // 새벽 0~1시라면 전날 23시 예보 사용
        if (baseHour == -1) {
            baseDate = baseDate.minusDays(1);
            baseHour = 23;
        }

        String date = baseDate.format(
                DateTimeFormatter.ofPattern("yyyyMMdd")
        );

        String time = String.format("%02d00", baseHour);

        return new ForecastTime(date, time);
    }
}