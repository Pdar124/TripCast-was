package com.tripcast.tourweather.tourspot.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcast.tourweather.climate.ClimateIndexService;
import com.tripcast.tourweather.climate.dto.ClimateIndexResponse;
import com.tripcast.tourweather.tourspot.TourSpot;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseStopResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseStopWeatherResponse;
import com.tripcast.tourweather.tourspot.course.dto.TourCourseWeatherResponse;
import com.tripcast.tourweather.weather.WeatherService;
import com.tripcast.tourweather.weather.dto.WeatherResponse;
import com.tripcast.tourweather.weather.util.GridCoordinate;
import com.tripcast.tourweather.weather.util.LatLonToGridConverter;

@Service
@Transactional(readOnly = true)
public class TourCourseService {

    private static final Logger log =
            LoggerFactory.getLogger(TourCourseService.class);

    private final TourCourseRepository courseRepository;
    private final TourCourseStopRepository courseStopRepository;
    private final ClimateIndexService climateIndexService;
    private final WeatherService weatherService;

    public TourCourseService(
            TourCourseRepository courseRepository,
            TourCourseStopRepository courseStopRepository,
            ClimateIndexService climateIndexService,
            WeatherService weatherService
    ) {
        this.courseRepository = courseRepository;
        this.courseStopRepository = courseStopRepository;
        this.climateIndexService = climateIndexService;
        this.weatherService = weatherService;
    }

    public TourCourseResponse getCourse(Long courseId) {
        TourCourse course = findCourse(courseId);
        List<TourCourseStopResponse> stops = findStops(courseId)
                .stream()
                .map(TourCourseStopResponse::from)
                .toList();

        return new TourCourseResponse(
                course.getId(),
                course.getSourceCourseId(),
                stops
        );
    }

    public TourCourseWeatherResponse getCourseWeather(Long courseId) {
        TourCourse course = findCourse(courseId);
        Map<String, Optional<ClimateIndexResponse>> climateByRegion =
                new HashMap<>();

        List<TourCourseStopWeatherResponse> stops = findStops(courseId)
                .stream()
                .map(stop -> toWeatherResponse(stop, climateByRegion))
                .toList();

        return new TourCourseWeatherResponse(
                course.getId(),
                course.getSourceCourseId(),
                stops
        );
    }

    private TourCourse findCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new TourCourseNotFoundException(courseId));
    }

    private List<TourCourseStop> findStops(Long courseId) {
        return courseStopRepository
                .findByCourseIdOrderByCourseOrderAsc(courseId);
    }

    private TourCourseStopWeatherResponse toWeatherResponse(
            TourCourseStop stop,
            Map<String, Optional<ClimateIndexResponse>> climateByRegion
    ) {
        Optional<ClimateIndexResponse> climateIndex = climateByRegion
                .computeIfAbsent(
                        stop.getRegionId(),
                        regionId -> Optional.ofNullable(
                                climateIndexService.findLatestOrNull(regionId)
                        )
                );

        return new TourCourseStopWeatherResponse(
                TourCourseStopResponse.from(stop),
                climateIndex.orElse(null),
                getWeatherOrNull(stop)
        );
    }

    private WeatherResponse getWeatherOrNull(TourCourseStop stop) {
        TourSpot tourSpot = stop.getTourSpot();
        try {
            GridCoordinate grid = LatLonToGridConverter.convert(
                    tourSpot.getLatitude(),
                    tourSpot.getLongitude()
            );
            return weatherService.getWeather(grid.nx(), grid.ny());
        } catch (RuntimeException exception) {
            log.warn(
                    "코스 관광지 동네예보 조회 실패. courseId={}, tourSpotId={}",
                    stop.getCourse().getId(),
                    tourSpot.getId(),
                    exception
            );
            return null;
        }
    }
}
