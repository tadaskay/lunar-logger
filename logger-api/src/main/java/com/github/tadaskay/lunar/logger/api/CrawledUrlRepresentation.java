package com.github.tadaskay.lunar.logger.api;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CrawledUrlRepresentation {

    private String id;
    private URL url;
    private boolean celebritiesReceived;
    private List<CelebrityRepresentation> celebrities = new ArrayList<>();
    private RemoteKeyRepresentation remoteKey;
}
