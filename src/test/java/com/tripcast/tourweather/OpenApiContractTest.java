package com.tripcast.tourweather;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "springdoc.api-docs.enabled=true",
        "springdoc.swagger-ui.enabled=true"
})
@AutoConfigureMockMvc
class OpenApiContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 신규_관광코스와_관광기후지수_API가_OpenAPI에_노출된다()
            throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.paths['/api/regions/{regionId}/climate-index']"
                ).exists())
                .andExpect(jsonPath(
                        "$.paths['/api/regions/{regionId}/climate-index/range']"
                ).exists())
                .andExpect(jsonPath(
                        "$.paths['/api/tour-courses/{courseId}']"
                ).exists())
                .andExpect(jsonPath(
                        "$.paths['/api/tour-courses/{courseId}/weather']"
                ).exists());
    }
}
