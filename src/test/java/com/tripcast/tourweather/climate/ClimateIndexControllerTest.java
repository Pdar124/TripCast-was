package com.tripcast.tourweather.climate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;

@WebMvcTest(ClimateIndexController.class)
class ClimateIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClimateIndexService climateIndexService;

    @Test
    void 지역의_최근_관광기후지수를_조회한다() throws Exception {
        when(climateIndexService.getLatest("1111000000"))
                .thenReturn(new ClimateIndexResponse(
                        "1111000000",
                        LocalDate.of(2026, 9, 20),
                        82,
                        "매우 좋음"
                ));

        mockMvc.perform(get("/api/regions/1111000000/climate-index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.regionId").value("1111000000"))
                .andExpect(jsonPath("$.date").value("2026-09-20"))
                .andExpect(jsonPath("$.score").value(82))
                .andExpect(jsonPath("$.grade").value("매우 좋음"));
    }

    @Test
    void 지수가_없는_지역은_404를_반환한다() throws Exception {
        when(climateIndexService.getLatest("9999999999"))
                .thenThrow(new RegionClimateIndexNotFoundException(
                        "9999999999"
                ));

        mockMvc.perform(get("/api/regions/9999999999/climate-index"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("관광기후지수를 찾을 수 없습니다: 9999999999"));
    }

    @Test
    void 기간별_관광기후지수를_조회한다() throws Exception {
        when(climateIndexService.getRange(
                "1111000000",
                LocalDate.of(2026, 9, 19),
                LocalDate.of(2026, 9, 20)
        )).thenReturn(List.of(
                new ClimateIndexResponse(
                        "1111000000",
                        LocalDate.of(2026, 9, 19),
                        65,
                        "좋음"
                ),
                new ClimateIndexResponse(
                        "1111000000",
                        LocalDate.of(2026, 9, 20),
                        52,
                        "보통"
                )
        ));

        mockMvc.perform(get("/api/regions/1111000000/climate-index/range")
                        .param("from", "2026-09-19")
                        .param("to", "2026-09-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value("2026-09-19"))
                .andExpect(jsonPath("$[1].date").value("2026-09-20"));
    }
}
