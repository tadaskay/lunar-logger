package com.github.tadaskay.lunar.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
            .rootUri("http://localhost:8080")
            .build();
    }

    @Bean
    Randomizer randomizer() {
        return new Randomizer();
    }

    @Bean
    Crawler crawler() {
        return new RandomCrawler(restTemplate(), randomizer());
    }
}
