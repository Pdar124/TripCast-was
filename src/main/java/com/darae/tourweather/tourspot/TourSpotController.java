package com.darae.tourweather.tourspot;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.darae.tourweather.tourspot.dto.TourSpotResponse;
import com.darae.tourweather.tourspot.dto.TourSpotWeatherResponse;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/tour-spots")
public class TourSpotController {

    private final TourSpotService tourSpotService;

    public TourSpotController(
            TourSpotService tourSpotService
    ) {
        this.tourSpotService = tourSpotService;
    }

    @GetMapping
    public Page<TourSpotResponse> search(
            @RequestParam(defaultValue = "")
            String keyword,

            @RequestParam(defaultValue = "0")
            @Min(0)
            int page,

            @RequestParam(defaultValue = "20")
            @Min(1)
            @Max(100)
            int size
    ) {
        return tourSpotService.search(
                keyword,
                page,
                size
        );
    }

    @GetMapping("/{id}/weather")
    public TourSpotWeatherResponse getWeather(
            @PathVariable Long id
    ) {
        return tourSpotService.getWeather(id);
    }
}