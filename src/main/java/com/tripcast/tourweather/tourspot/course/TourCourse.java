package com.tripcast.tourweather.tourspot.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "tour_course",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tour_course_source_id",
                        columnNames = "source_course_id"
                )
        }
)
public class TourCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "source_course_id",
            nullable = false,
            length = 20
    )
    private String sourceCourseId;

    protected TourCourse() {
    }

    public TourCourse(String sourceCourseId) {
        this.sourceCourseId = sourceCourseId;
    }

    public Long getId() {
        return id;
    }

    public String getSourceCourseId() {
        return sourceCourseId;
    }
}