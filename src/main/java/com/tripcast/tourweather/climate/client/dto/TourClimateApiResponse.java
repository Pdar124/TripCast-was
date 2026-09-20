package com.tripcast.tourweather.climate.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TourClimateApiResponse(Response response) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(Header header, Body body) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Header(String resultCode, String resultMsg) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Body(
            String dataType,
            Items items,
            int numOfRows,
            int pageNo,
            int totalCount
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Items(List<Item> item) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
            String tm,
            String cityAreaId,
            @JsonAlias({"totalCityName", "totlCityName"})
            String totalCityName,
            String doName,
            String cityName,
            String kmaTci,
            @JsonAlias({"TCI_GRADE", "tciGrade"})
            String tciGrade
    ) {
    }
}
