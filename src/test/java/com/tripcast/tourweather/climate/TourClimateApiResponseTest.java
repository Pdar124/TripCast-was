package com.tripcast.tourweather.climate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripcast.tourweather.climate.client.dto.TourClimateApiResponse;
import com.tripcast.tourweather.climate.client.dto.TourClimateApiResponse.Item;

class TourClimateApiResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 공공데이터포털의_대문자_등급_필드를_역직렬화한다() throws Exception {
        String json = """
                {
                  "response": {
                    "header": {
                      "resultCode": "00",
                      "resultMsg": "NORMAL_SERVICE"
                    },
                    "body": {
                      "dataType": "JSON",
                      "items": {
                        "item": [
                          {
                            "tm": "2026-09-20 06:00",
                            "totalCityName": "제주 서귀포시",
                            "doName": "제주",
                            "cityName": "서귀포시",
                            "cityAreaId": "5013000000",
                            "kmaTci": "52",
                            "TCI_GRADE": "0"
                          }
                        ]
                      },
                      "numOfRows": 10,
                      "pageNo": 1,
                      "totalCount": 1
                    }
                  }
                }
                """;

        TourClimateApiResponse response = objectMapper.readValue(
                json,
                TourClimateApiResponse.class
        );
        Item item = response.response().body().items().item().getFirst();

        assertAll(
                () -> assertEquals("5013000000", item.cityAreaId()),
                () -> assertEquals("제주 서귀포시", item.totalCityName()),
                () -> assertEquals("52", item.kmaTci()),
                () -> assertEquals("0", item.tciGrade())
        );
    }

    @Test
    void 한_건의_item이_객체로_와도_목록으로_역직렬화한다() throws Exception {
        String json = """
                {
                  "response": {
                    "header": {
                      "resultCode": "00",
                      "resultMsg": "NORMAL_SERVICE"
                    },
                    "body": {
                      "dataType": "JSON",
                      "items": {
                        "item": {
                          "tm": "2026-09-21 06:00",
                          "cityAreaId": "5013000000",
                          "kmaTci": "52",
                          "TCI_GRADE": "0"
                        }
                      },
                      "numOfRows": 10,
                      "pageNo": 1,
                      "totalCount": 1
                    }
                  }
                }
                """;

        TourClimateApiResponse response = objectMapper.readValue(
                json,
                TourClimateApiResponse.class
        );

        assertEquals(1, response.response().body().items().item().size());
    }
}
