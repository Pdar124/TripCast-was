package com.darae.tourweather.tourspot.course;

import com.darae.tourweather.tourspot.TourSpot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "tour_course_stop",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_stop_source_id",
                        columnNames = "source_spot_id"
                ),
                @UniqueConstraint(
                        name = "uk_course_stop_order",
                        columnNames = {
                                "course_id",
                                "course_order"
                        }
                )
        }
)
public class TourCourseStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "source_spot_id",
            nullable = false,
            length = 20
    )
    private String sourceSpotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private TourCourse course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id", nullable = false)
    private TourSpot tourSpot;

    @Column(name = "region_id", nullable = false, length = 20)
    private String regionId;

    @Column(name = "course_order", nullable = false)
    private int courseOrder;

    @Column(name = "travel_time", nullable = false)
    private int travelTime;

    @Column(name = "indoor_type", nullable = false, length = 10)
    private String indoorType;

    @Column(name = "theme_code", nullable = false, length = 10)
    private String themeCode;

    @Column(name = "theme_name", nullable = false, length = 50)
    private String themeName;

    protected TourCourseStop() {
    }

    public TourCourseStop(
            String sourceSpotId,
            TourCourse course,
            TourSpot tourSpot,
            String regionId,
            int courseOrder,
            int travelTime,
            String indoorType,
            String themeCode,
            String themeName
    ) {
        this.sourceSpotId = sourceSpotId;
        this.course = course;
        this.tourSpot = tourSpot;
        this.regionId = regionId;
        this.courseOrder = courseOrder;
        this.travelTime = travelTime;
        this.indoorType = indoorType;
        this.themeCode = themeCode;
        this.themeName = themeName;
    }
}