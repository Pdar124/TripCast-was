package com.tripcast.tourweather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tripcast.tourweather.tourspot.TourSpot;
import com.tripcast.tourweather.tourspot.TourSpotRepository;

@SpringBootApplication
public class TourWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourWeatherApplication.class, args);
    }
}