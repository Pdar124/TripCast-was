package com.tripcast.tourweather.tourspot.importer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class TourSpotCsvReader {

    public List<TourSpotCsvRow> read() {
        ClassPathResource resource =
                new ClassPathResource("data/tour_spots.csv");

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .get();

        try (
                Reader reader = new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.UTF_8
                );
                CSVParser parser = format.parse(reader)
        ) {
            return parser.stream()
                    .map(this::toRow)
                    .toList();

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "관광지 CSV 파일을 읽을 수 없습니다.",
                    exception
            );
        }
    }

    private TourSpotCsvRow toRow(CSVRecord record) {
        try {
            return new TourSpotCsvRow(
                    record.get("테마분류"),
                    record.get("코스 아이디"),
                    record.get("관광지 아이디"),
                    record.get("지역 아이디"),
                    record.get("관광지명"),
                    Double.parseDouble(record.get("경도(도)")),
                    Double.parseDouble(record.get("위도(도)")),
                    Integer.parseInt(record.get("코스순서")),
                    Integer.parseInt(record.get("이동시간")),
                    record.get("실내구분"),
                    record.get("테마명")
            );

        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(
                    "CSV 데이터 " + record.getRecordNumber()
                            + "번째 행을 변환할 수 없습니다.",
                    exception
            );
        }
    }
}