package com.tripcast.tourweather.tourspot;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.tripcast.tourweather.tourspot.dto.TourSpotResponse;
import com.tripcast.tourweather.tourspot.dto.TourSpotWeatherResponse;
import com.tripcast.tourweather.weather.WeatherService;
import com.tripcast.tourweather.weather.dto.WeatherResponse;
import com.tripcast.tourweather.weather.util.GridCoordinate;
import com.tripcast.tourweather.weather.util.LatLonToGridConverter;

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

    public Page<TourSpotResponse> search(
            String keyword,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by("name").ascending());

        return tourSpotRepository
                .findByNameContaining(
                        keyword,
                        pageRequest)
                .map(TourSpotResponse::from);
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