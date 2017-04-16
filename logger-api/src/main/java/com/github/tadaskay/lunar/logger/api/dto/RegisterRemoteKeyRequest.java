package com.github.tadaskay.lunar.logger.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RegisterRemoteKeyRequest {

    @NotNull
    private final String remoteKey;

    @JsonCreator
    public RegisterRemoteKeyRequest(@JsonProperty("remoteKey") String remoteKey) {
        this.remoteKey = remoteKey;
    }
}
