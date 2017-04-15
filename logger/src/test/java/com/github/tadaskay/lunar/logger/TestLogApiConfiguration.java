package com.github.tadaskay.lunar.logger;

import com.github.tadaskay.lunar.logger.api.LogApi;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestLogApiConfiguration {

    @Bean
    public LogApi initializeLogApi(TestRestTemplate restTemplate) {
        LogApi.restTemplate = restTemplate.getRestTemplate();
        return new LogApi();
    }
}
