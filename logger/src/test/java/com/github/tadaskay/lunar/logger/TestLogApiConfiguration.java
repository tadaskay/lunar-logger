package com.github.tadaskay.lunar.logger;

import com.github.tadaskay.lunar.logger.api.LunarLogApi;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestLogApiConfiguration {

    @Bean
    public LunarLogApi initializeLogApi(TestRestTemplate restTemplate) {
        LunarLogApi.restTemplate = restTemplate.getRestTemplate();
        return new LunarLogApi();
    }
}
