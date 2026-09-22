package com.tripcast.tourweather.tourspot.course;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tripcast.tourweather.tourspot.course.dto.TourCourseResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseStopResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseStopWeatherResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseWeatherResponse;

@WebMvcTest(TourCourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class TourCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TourCourseService tourCourseService;

    @Test
    void 코스와_관광지_목록을_조회한다() throws Exception {
        TourCourseStopResponse stop = new TourCourseStopResponse(
                10L,
                "1111000001",
                20L,
                "서울시청",
                37.5665,
                126.9780,
                "1111000000",
                1,
                30,
                "실외",
                "01",
                "역사"
        );
        when(tourCourseService.getCourse(1L))
                .thenReturn(new TourCourseResponse(
                        1L,
                        "TH01",
                        List.of(stop)
                ));

        mockMvc.perform(get("/api/tour-courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.sourceCourseId").value("TH01"))
                .andExpect(jsonPath("$.stops[0].tourSpotName")
                        .value("서울시청"))
                .andExpect(jsonPath("$.stops[0].courseOrder").value(1));
    }

    @Test
    void 존재하지_않는_코스는_404를_반환한다() throws Exception {
        when(tourCourseService.getCourse(999L))
                .thenThrow(new TourCourseNotFoundException(999L));

        mockMvc.perform(get("/api/tour-courses/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("관광코스를 찾을 수 없습니다: 999"));
    }

    @Test
    void 코스_통합_날씨의_부분_응답을_반환한다() throws Exception {
        TourCourseStopResponse stop = new TourCourseStopResponse(
                10L,
                "1111000001",
                20L,
                "서울시청",
                37.5665,
                126.9780,
                "1111000000",
                1,
                30,
                "실외",
                "01",
                "역사"
        );
        when(tourCourseService.getCourseWeather(1L))
                .thenReturn(new TourCourseWeatherResponse(
                        1L,
                        "TH01",
                        List.of(new TourCourseStopWeatherResponse(
                                stop,
                                null,
                                null
                        ))
                ));

        mockMvc.perform(get("/api/tour-courses/1/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceCourseId").value("TH01"))
                .andExpect(jsonPath("$.stops[0].spot.tourSpotName")
                        .value("서울시청"))
                .andExpect(jsonPath("$.stops[0].weather").isEmpty());
    }
}
