package com.darae.tourweather.weather;

import org.springframework.stereotype.Service;

import com.darae.tourweather.weather.client.KmaWeatherClient;

@Service
public class WeatherService {

    private final KmaWeatherClient kmaWeatherClient;

    public WeatherService(KmaWeatherClient kmaWeatherClient) {
        this.kmaWeatherClient = kmaWeatherClient;
    }

    public String getWeather() {
        return kmaWeatherClient.getVillageForecast();
    }
}