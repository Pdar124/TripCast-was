package com.tripcast.tourweather.tourspot.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tripcast.tourweather.climate.util.ClimateRegionIdNormalizer;
import com.tripcast.tourweather.tourspot.TourSpot;
import com.tripcast.tourweather.tourspot.TourSpotRepository;
import com.tripcast.tourweather.tourspot.course.TourCourse;
import com.tripcast.tourweather.tourspot.course.TourCourseRepository;
import com.tripcast.tourweather.tourspot.course.TourCourseStop;
import com.tripcast.tourweather.tourspot.course.TourCourseStopRepository;

@Component
@ConditionalOnProperty(
        name = "app.tour-spot-import.enabled",
        havingValue = "true"
)
public class TourSpotDataImporter
        implements ApplicationRunner {

    private static final Logger log =
            LoggerFactory.getLogger(
                    TourSpotDataImporter.class
            );

    private final TourSpotCsvReader csvReader;
    private final TourSpotRepository tourSpotRepository;
    private final TourCourseRepository courseRepository;
    private final TourCourseStopRepository courseStopRepository;

    public TourSpotDataImporter(
            TourSpotCsvReader csvReader,
            TourSpotRepository tourSpotRepository,
            TourCourseRepository courseRepository,
            TourCourseStopRepository courseStopRepository
    ) {
        this.csvReader = csvReader;
        this.tourSpotRepository = tourSpotRepository;
        this.courseRepository = courseRepository;
        this.courseStopRepository = courseStopRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        int savedCount = 0;
        int skippedCount = 0;

        for (TourSpotCsvRow row : csvReader.read()) {

            if (courseStopRepository.existsBySourceSpotId(
                    row.sourceSpotId()
            )) {
                skippedCount++;
                continue;
            }

            TourCourse course = courseRepository
                    .findBySourceCourseId(row.sourceCourseId())
                    .orElseGet(() ->
                            courseRepository.save(
                                    new TourCourse(
                                            row.sourceCourseId()
                                    )
                            )
                    );

            TourSpot tourSpot = tourSpotRepository
                    .findByNameAndLatitudeAndLongitude(
                            row.name(),
                            row.latitude(),
                            row.longitude()
                    )
                    .orElseGet(() ->
                            tourSpotRepository.save(
                                    new TourSpot(
                                            row.name(),
                                            row.latitude(),
                                            row.longitude()
                                    )
                            )
                    );

            TourCourseStop courseStop =
                    new TourCourseStop(
                            row.sourceSpotId(),
                            course,
                            tourSpot,
                            ClimateRegionIdNormalizer.normalize(
                                    row.regionId()
                            ),
                            row.courseOrder(),
                            row.travelTime(),
                            normalizeIndoorType(
                                    row.indoorType()
                            ),
                            row.themeCode(),
                            row.themeName()
                    );

            courseStopRepository.save(courseStop);
            savedCount++;
        }

        log.info(
                "관광지 CSV 적재 완료: 저장={}, 건너뜀={}",
                savedCount,
                skippedCount
        );
    }

    private String normalizeIndoorType(String value) {
        return switch (value.trim()) {
            case "실내", "살내" -> "실내";
            case "실외" -> "실외";
            default -> {
                log.warn(
                        "알 수 없는 실내구분 값: {}",
                        value
                );
                yield "미상";
            }
        };
    }
}
