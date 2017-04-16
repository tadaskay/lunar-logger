package com.github.tadaskay.lunar.logger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URL;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class ApiDocumentationConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .alternateTypeRules(AlternateTypeRules.newRule(URL.class, String.class))
            .consumes(singleton(APPLICATION_JSON_VALUE))
            .produces(singleton(APPLICATION_JSON_VALUE))
            .select()
            .paths(Predicates.or(asList(
                ant("/api/**"),
                ant("/metrics"),
                ant("/health")
            )))
            .build();
    }

}
