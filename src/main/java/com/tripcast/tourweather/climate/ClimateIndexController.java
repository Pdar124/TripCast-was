package com.tripcast.tourweather.climate;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "관광기후지수",
        description = "시군구별 관광기후지수 조회 API"
)
@RestController
@RequestMapping("/api/regions")
public class ClimateIndexController {

    private final ClimateIndexService climateIndexService;

    public ClimateIndexController(ClimateIndexService climateIndexService) {
        this.climateIndexService = climateIndexService;
    }

    @Operation(
            summary = "최근 관광기후지수 조회",
            description = "시군구 코드로 가장 최근에 저장된 관광기후지수를 조회합니다."
    )
    @GetMapping("/{regionId}/climate-index")
    public ClimateIndexResponse getLatest(
            @PathVariable String regionId
    ) {
        return climateIndexService.getLatest(regionId);
    }

    @Operation(
            summary = "기간별 관광기후지수 조회",
            description = "기간 내 저장된 일자만 오래된 순서로 반환합니다. 데이터가 없는 날짜는 제외합니다."
    )
    @GetMapping("/{regionId}/climate-index/range")
    public List<ClimateIndexResponse> getRange(
            @PathVariable String regionId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        return climateIndexService.getRange(regionId, from, to);
    }
}
