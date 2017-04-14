package com.github.tadaskay.lunar.logger.remotekey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteKey {
    private final String value;

    @JsonCreator
    public RemoteKey(@JsonProperty String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
