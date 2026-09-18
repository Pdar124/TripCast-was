package com.darae.tourweather.tourspot;

import java.util.List;

import org.springframework.stereotype.Service;

import com.darae.tourweather.tourspot.dto.TourSpotResponse;
import com.darae.tourweather.tourspot.dto.TourSpotWeatherResponse;
import com.darae.tourweather.weather.WeatherService;
import com.darae.tourweather.weather.dto.WeatherResponse;
import com.darae.tourweather.weather.util.GridCoordinate;
import com.darae.tourweather.weather.util.LatLonToGridConverter;

@Service
public class TourSpotService {

    private final TourSpotRepository tourSpotRepository;
    private final WeatherService weatherService;

    public TourSpotService(
            TourSpotRepository tourSpotRepository,
            WeatherService weatherService) {
        this.tourSpotRepository = tourSpotRepository;
        this.weatherService = weatherService;
    }

    public List<TourSpotResponse> search(String keyword) {
        return tourSpotRepository
                .findByNameContaining(keyword)
                .stream()
                .map(TourSpotResponse::from)
                .toList();
    }

    public TourSpotWeatherResponse getWeather(
            Long tourSpotId) {
        TourSpot tourSpot = tourSpotRepository
                .findById(tourSpotId)
                .orElseThrow(() -> new TourSpotNotFoundException(
                        tourSpotId));

        GridCoordinate grid = LatLonToGridConverter.convert(
                tourSpot.getLatitude(),
                tourSpot.getLongitude());

        WeatherResponse weather = weatherService.getWeather(
                grid.nx(),
                grid.ny());

        return new TourSpotWeatherResponse(
                tourSpot.getId(),
                tourSpot.getName(),
                tourSpot.getLatitude(),
                tourSpot.getLongitude(),
                weather);
    }
}