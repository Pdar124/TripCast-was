package com.darae.tourweather.weather;

import com.darae.tourweather.weather.dto.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping
    public WeatherResponse getWeather() {

        return new WeatherResponse(
                "날씨 API 서버가 정상 작동합니다."
        );
    }
}