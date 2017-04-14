package com.github.tadaskay.lunar.logger.url;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
class CreateCrawledUrlRequest {

    @NotNull
    private URL url;
}
