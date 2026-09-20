package com.tripcast.tourweather.climate.domain;

import java.math.BigDecimal;
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
        name = "region_climate_index",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_region_climate_date",
                columnNames = {"region_id", "base_date"}
        )
)
public class RegionClimateIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_id", nullable = false, length = 20)
    private String regionId;

    @Column(name = "base_date", nullable = false)
    private LocalDate baseDate;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(nullable = false, length = 20)
    private String grade;

    protected RegionClimateIndex() {
    }

    public RegionClimateIndex(
            String regionId,
            LocalDate baseDate,
            BigDecimal score,
            String grade
    ) {
        this.regionId = regionId;
        this.baseDate = baseDate;
        this.score = score;
        this.grade = grade;
    }

    public void update(BigDecimal score, String grade) {
        this.score = score;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public LocalDate getBaseDate() {
        return baseDate;
    }

    public BigDecimal getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }
}
