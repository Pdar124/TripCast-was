package com.tripcast.tourweather.climate;

public class InvalidClimateIndexRangeException extends RuntimeException {

    public InvalidClimateIndexRangeException() {
        super("조회 시작일은 종료일보다 늦을 수 없습니다.");
    }
}
