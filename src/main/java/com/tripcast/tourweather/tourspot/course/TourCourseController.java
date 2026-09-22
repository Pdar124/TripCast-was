package com.tripcast.tourweather.tourspot.course;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripcast.tourweather.tourspot.course.dto.CourseRecommendationResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseWeatherResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Tag(
        name = "관광코스",
        description = "관광코스 상세 및 통합 날씨 조회 API"
)
@Validated
@RestController
@RequestMapping("/api/tour-courses")
public class TourCourseController {

    private final TourCourseService tourCourseService;

    public TourCourseController(TourCourseService tourCourseService) {
        this.tourCourseService = tourCourseService;
    }

    @Operation(
            summary = "오늘 가기 좋은 코스 추천",
            description = "코스 첫 관광지가 속한 지역의 관광기후지수가 높은 순으로 정렬해 반환합니다. 지수 데이터가 없는 코스는 제외됩니다."
    )
    @GetMapping("/recommendations")
    public List<CourseRecommendationResponse> getRecommendations(
            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(50)
            int limit
    ) {
        return tourCourseService.getRecommendations(limit);
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
