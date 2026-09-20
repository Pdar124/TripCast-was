package com.tripcast.tourweather.climate.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "spot_weather_index",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_spot_weather_date",
                columnNames = {"source_spot_id", "base_date"}
        )
)
public class SpotWeatherIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_spot_id", nullable = false, length = 20)
    private String sourceSpotId;

    @Column(name = "base_date", nullable = false)
    private LocalDate baseDate;

    @Column(name = "feels_like_temp")
    private Double feelsLikeTemp;

    @Column(name = "uv_index")
    private Integer uvIndex;

    protected SpotWeatherIndex() {
    }

    public SpotWeatherIndex(
            String sourceSpotId,
            LocalDate baseDate,
            Double feelsLikeTemp,
            Integer uvIndex
    ) {
        this.sourceSpotId = sourceSpotId;
        this.baseDate = baseDate;
        this.feelsLikeTemp = feelsLikeTemp;
        this.uvIndex = uvIndex;
    }

    public void update(Double feelsLikeTemp, Integer uvIndex) {
        this.feelsLikeTemp = feelsLikeTemp;
        this.uvIndex = uvIndex;
    }

    public Long getId() {
        return id;
    }

    public String getSourceSpotId() {
        return sourceSpotId;
    }

    public LocalDate getBaseDate() {
        return baseDate;
    }

    public Double getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }
}
