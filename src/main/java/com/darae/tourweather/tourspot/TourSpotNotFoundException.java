package com.darae.tourweather.tourspot;

public class TourSpotNotFoundException
        extends RuntimeException {

    public TourSpotNotFoundException(Long id) {
        super("관광지를 찾을 수 없습니다: " + id);
    }
}