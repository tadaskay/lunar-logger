package com.github.tadaskay.lunar.logger.api.dto;

import lombok.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
public class CrawledUrlRepresentation {

    private String id;
    private URL url;
    private boolean celebritiesReceived;
    private List<CelebrityRepresentation> celebrities = new ArrayList<>();
    private RemoteKeyRepresentation remoteKey;
}
