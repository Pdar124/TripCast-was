package com.tripcast.tourweather.tourspot.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripcast.tourweather.tourspot.course.dto.TourCourseResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseWeatherResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "관광코스",
        description = "관광코스 상세 및 통합 날씨 조회 API"
)
@RestController
@RequestMapping("/api/tour-courses")
public class TourCourseController {

    private final TourCourseService tourCourseService;

    public TourCourseController(TourCourseService tourCourseService) {
        this.tourCourseService = tourCourseService;
    }

    @Operation(
            summary = "관광코스 상세 조회",
            description = "관광코스와 코스 내 관광지 목록을 방문 순서대로 조회합니다."
    )
    @GetMapping("/{courseId}")
    public TourCourseResponse getCourse(@PathVariable Long courseId) {
        return tourCourseService.getCourse(courseId);
    }

    @Operation(
            summary = "관광코스 통합 날씨 조회",
            description = "코스 내 관광지별 동네예보와 지역 관광기후지수를 함께 조회합니다. 동네예보 실패 시 해당 weather만 null로 반환합니다."
    )
    @GetMapping("/{courseId}/weather")
    public TourCourseWeatherResponse getCourseWeather(
            @PathVariable Long courseId
    ) {
        return tourCourseService.getCourseWeather(courseId);
    }
}
