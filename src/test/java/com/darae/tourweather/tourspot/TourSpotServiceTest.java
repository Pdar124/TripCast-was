package com.darae.tourweather.tourspot;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.darae.tourweather.tourspot.dto.TourSpotWeatherResponse;
import com.darae.tourweather.weather.WeatherService;
import com.darae.tourweather.weather.dto.WeatherResponse;

@ExtendWith(MockitoExtension.class)
class TourSpotServiceTest {

    @Mock
    private TourSpotRepository tourSpotRepository;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private TourSpotService tourSpotService;

    @Test
    void 관광지_좌표를_변환해서_날씨를_조회한다() {
        TourSpot tourSpot = new TourSpot(
                "서울시청",
                37.5665,
                126.9780);

        WeatherResponse weather = new WeatherResponse(
                "20260920",
                "1200",
                22.0,
                20,
                "맑음");

        when(tourSpotRepository.findById(1L))
                .thenReturn(Optional.of(tourSpot));
        when(weatherService.getWeather(60, 127))
                .thenReturn(weather);

        TourSpotWeatherResponse result =
                tourSpotService.getWeather(1L);

        assertAll(
                () -> assertEquals("서울시청", result.tourSpotName()),
                () -> assertEquals(37.5665, result.latitude()),
                () -> assertEquals(126.9780, result.longitude()),
                () -> assertEquals(weather, result.weather()));

        verify(tourSpotRepository).findById(1L);
        verify(weatherService).getWeather(60, 127);
    }

    @Test
    void 존재하지_않는_관광지는_예외가_발생한다() {
        when(tourSpotRepository.findById(999L))
                .thenReturn(Optional.empty());

        TourSpotNotFoundException exception = assertThrows(
                TourSpotNotFoundException.class,
                () -> tourSpotService.getWeather(999L));

        assertEquals(
                "관광지를 찾을 수 없습니다: 999",
                exception.getMessage());
        verifyNoInteractions(weatherService);
    }
}
