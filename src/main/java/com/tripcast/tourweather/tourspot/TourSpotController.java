package com.tripcast.tourweather.tourspot;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripcast.tourweather.tourspot.dto.TourSpotResponse;
import com.tripcast.tourweather.tourspot.dto.TourSpotWeatherResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Tag(
        name = "관광지",
        description = "관광지 검색 및 날씨 조회 API"
)
@Validated
@RestController
@RequestMapping("/api/tour-spots")
public class TourSpotController {

    private final TourSpotService tourSpotService;

    public TourSpotController(
            TourSpotService tourSpotService
    ) {
        this.tourSpotService = tourSpotService;
    }

    @Operation(
            summary = "관광지 검색",
            description = "이름에 검색어가 포함된 관광지를 페이지 단위로 조회합니다."
    )
    @GetMapping
    public Page<TourSpotResponse> search(
            @RequestParam(defaultValue = "")
            String keyword,

            @RequestParam(defaultValue = "0")
            @Min(0)
            int page,

            @RequestParam(defaultValue = "20")
            @Min(1)
            @Max(100)
            int size
    ) {
        return tourSpotService.search(
                keyword,
                page,
                size
        );
    }

    @Operation(
            summary = "관광지 날씨 조회",
            description = "관광지 ID를 이용해 위치와 기상청 단기예보를 조회합니다."
    )
    @GetMapping("/{id}/weather")
    public TourSpotWeatherResponse getWeather(
            @PathVariable Long id
    ) {
        return tourSpotService.getWeather(id);
    }
}