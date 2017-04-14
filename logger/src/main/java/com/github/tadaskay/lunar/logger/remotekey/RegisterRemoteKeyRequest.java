package com.github.tadaskay.lunar.logger.remotekey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
class RegisterRemoteKeyRequest {

    @NotNull
    private final String remoteKey;

    @JsonCreator
    RegisterRemoteKeyRequest(@JsonProperty String remoteKey) {
        this.remoteKey = remoteKey;
    }
}
