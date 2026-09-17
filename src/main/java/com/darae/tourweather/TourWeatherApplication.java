package com.darae.tourweather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.darae.tourweather.tourspot.TourSpot;
import com.darae.tourweather.tourspot.TourSpotRepository;

@SpringBootApplication
public class TourWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourWeatherApplication.class, args);
    }
}