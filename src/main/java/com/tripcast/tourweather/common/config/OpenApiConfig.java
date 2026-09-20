package com.tripcast.tourweather.common.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TripCast API",
                version = "v1",
                description = """
                        관광지를 검색하고 해당 관광지의
                        날씨를 조회하는 백엔드 API입니다.
                        """
        )
)
public class OpenApiConfig {
}