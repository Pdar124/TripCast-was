package com.darae.tourweather.tourspot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.darae.tourweather.weather.dto.WeatherResponse;
import com.darae.tourweather.tourspot.dto.TourSpotResponse;

@RestController
@RequestMapping("/api/tour-spots")
public class TourSpotController {

    private final TourSpotService tourSpotService;

    public TourSpotController(TourSpotService tourSpotService) {
        this.tourSpotService = tourSpotService;
    }

    @GetMapping
    public List<TourSpotResponse> search(
            @RequestParam String keyword) {
        return tourSpotService.search(keyword);
    }

    @GetMapping("/{id}/weather")
    public WeatherResponse getWeather(
            @PathVariable Long id) {
        return tourSpotService.getWeather(id);
    }
}