package com.github.tadaskay.lunar.logger.url;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tadaskay.lunar.logger.celebrities.Celebrity;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CrawledUrl {

    @Id
    private String id;
    private final URL url;
    private boolean celebritiesReceived;
    private List<Celebrity> celebrities = new ArrayList<>();
    private RemoteKey remoteKey;

    @JsonCreator
    CrawledUrl(@JsonProperty URL url) {
        this.url = url;
    }
}
