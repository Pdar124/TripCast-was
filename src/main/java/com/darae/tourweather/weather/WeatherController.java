package com.darae.tourweather.weather;

import com.darae.tourweather.weather.dto.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/health")
    public String health() {
        return "날씨 API 서버가 정상 작동합니다.";
    }

    @GetMapping
    public WeatherResponse getWeather() {
        return weatherService.getWeather();
    }
}