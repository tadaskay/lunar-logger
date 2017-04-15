package com.github.tadaskay.lunar.logger.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
public class CreateCrawledUrlRequest {

    @NotNull
    private final URL url;

    @JsonCreator
    public CreateCrawledUrlRequest(@JsonProperty URL url) {
        this.url = url;
    }
}
