package com.github.tadaskay.lunar.logger;

import com.github.tadaskay.lunar.logger.api.LogApi;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan
public class FixtureConfiguration {

    @Bean
    public LogApi initializeLogApi(TestRestTemplate restTemplate) {
        LogApi.restTemplate = restTemplate.getRestTemplate();
        return new LogApi();
    }
}
