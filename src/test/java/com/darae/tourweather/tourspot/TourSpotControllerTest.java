package com.darae.tourweather.tourspot;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.darae.tourweather.tourspot.dto.TourSpotResponse;

@WebMvcTest(TourSpotController.class)
class TourSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TourSpotService tourSpotService;

    @Test
    void 관광지를_페이지로_검색한다() throws Exception {
        TourSpotResponse tourSpot = new TourSpotResponse(
                1L,
                "서울시청",
                37.5665,
                126.9780);

        when(tourSpotService.search("서울", 0, 20))
                .thenReturn(new PageImpl<>(
                        List.of(tourSpot),
                        PageRequest.of(0, 20),
                        1));

        mockMvc.perform(get("/api/tour-spots")
                        .param("keyword", "서울")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("서울시청"))
                .andExpect(jsonPath("$.content[0].latitude").value(37.5665))
                .andExpect(jsonPath("$.content[0].longitude").value(126.9780))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(20));
    }

    @Test
    void 음수_페이지는_400을_반환한다() throws Exception {
        mockMvc.perform(get("/api/tour-spots")
                        .param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("요청 값이 올바르지 않습니다."));

        verifyNoInteractions(tourSpotService);
    }

    @Test
    void 페이지_크기가_100을_넘으면_400을_반환한다() throws Exception {
        mockMvc.perform(get("/api/tour-spots")
                        .param("size", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("요청 값이 올바르지 않습니다."));

        verifyNoInteractions(tourSpotService);
    }

    @Test
    void 존재하지_않는_관광지_날씨는_404를_반환한다() throws Exception {
        when(tourSpotService.getWeather(999L))
                .thenThrow(new TourSpotNotFoundException(999L));

        mockMvc.perform(get("/api/tour-spots/999/weather"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("관광지를 찾을 수 없습니다: 999"));
    }
}
