package com.darae.tourweather.tourspot;

import java.util.List;

import org.springframework.stereotype.Service;

import com.darae.tourweather.tourspot.dto.TourSpotResponse;

@Service
public class TourSpotService {

    private final TourSpotRepository tourSpotRepository;

    public TourSpotService(TourSpotRepository tourSpotRepository) {
        this.tourSpotRepository = tourSpotRepository;
    }

    public List<TourSpotResponse> search(String keyword) {
        return tourSpotRepository
                .findByNameContaining(keyword)
                .stream()
                .map(TourSpotResponse::from)
                .toList();
    }
}