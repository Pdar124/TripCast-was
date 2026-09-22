package com.tripcast.tourweather.tourspot.course;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import com.tripcast.tourweather.climate.ClimateIndexService;
import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.tourspot.TourSpot;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseSummaryResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseWeatherResponse;
import com.tripcast.tourweather.weather.WeatherService;

@ExtendWith(MockitoExtension.class)
class TourCourseServiceTest {

    @Mock
    private TourCourseRepository courseRepository;

    @Mock
    private TourCourseStopRepository courseStopRepository;

    @Mock
    private ClimateIndexService climateIndexService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private TourCourseService tourCourseService;

    @Test
    void 동네예보가_실패해도_지수를_포함한_부분_응답을_반환한다() {
        TourCourse course = new TourCourse("TH01");
        TourSpot tourSpot = new TourSpot("서울시청", 37.5665, 126.9780);
        TourCourseStop stop = new TourCourseStop(
                "1111000001",
                course,
                tourSpot,
                "1111051000",
                1,
                30,
                "실외",
                "01",
                "역사"
        );
        ClimateIndexResponse climateIndex = new ClimateIndexResponse(
                "1111000000",
                LocalDate.of(2026, 9, 20),
                new BigDecimal("0.82"),
                "매우좋음"
        );

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));
        when(courseStopRepository.findByCourseIdOrderByCourseOrderAsc(1L))
                .thenReturn(List.of(stop));
        when(climateIndexService.findLatestOrNull("1111000000"))
                .thenReturn(climateIndex);
        when(weatherService.getWeather(60, 127))
                .thenThrow(new IllegalStateException("기상청 API 실패"));

        TourCourseWeatherResponse result =
                tourCourseService.getCourseWeather(1L);

        assertAll(
                () -> assertEquals("TH01", result.sourceCourseId()),
                () -> assertEquals(1, result.stops().size()),
                () -> assertEquals(
                        climateIndex,
                        result.stops().get(0).climateIndex()
                ),
                () -> assertEquals(
                        "1111000000",
                        result.stops().get(0).spot().regionId()
                ),
                () -> assertNull(result.stops().get(0).weather())
        );
    }

    @Test
    void 코스_목록_조회시_첫_관광지명과_테마_정류지_수를_요약해서_반환한다() {
        TourCourse course = new TourCourse("TH01");
        ReflectionTestUtils.setField(course, "id", 1L);
        TourSpot tourSpot = new TourSpot("서울시청", 37.5665, 126.9780);
        TourCourseStop firstStop = new TourCourseStop(
                "1111000001",
                course,
                tourSpot,
                "1111051000",
                1,
                30,
                "실외",
                "01",
                "역사"
        );

        when(courseRepository.findAll(
                PageRequest.of(0, 20, Sort.by("id").ascending())
        )).thenReturn(new PageImpl<>(List.of(course)));
        when(courseStopRepository.findFirstByCourseIdOrderByCourseOrderAsc(1L))
                .thenReturn(Optional.of(firstStop));
        when(courseStopRepository.countByCourseId(1L)).thenReturn(3L);

        Page<TourCourseSummaryResponse> result =
                tourCourseService.searchCourses(0, 20);

        TourCourseSummaryResponse summary = result.getContent().get(0);
        assertAll(
                () -> assertEquals(1L, summary.courseId()),
                () -> assertEquals("TH01", summary.sourceCourseId()),
                () -> assertEquals("서울시청", summary.representativeSpotName()),
                () -> assertEquals("역사", summary.representativeThemeName()),
                () -> assertEquals(3L, summary.stopCount())
        );
    }
}
