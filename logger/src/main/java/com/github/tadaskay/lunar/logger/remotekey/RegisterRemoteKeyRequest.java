package com.github.tadaskay.lunar.logger.remotekey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

class RegisterRemoteKeyRequest {
    @NotNull
    private final String remoteKey;

    @JsonCreator
    RegisterRemoteKeyRequest(@JsonProperty("remoteKey") String remoteKey) {
        this.remoteKey = remoteKey;
    }

    public String getRemoteKey() {
        return remoteKey;
    }
}
