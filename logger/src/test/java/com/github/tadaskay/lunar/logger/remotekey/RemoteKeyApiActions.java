package com.github.tadaskay.lunar.logger.remotekey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;

import static org.springframework.http.HttpMethod.PUT;

@TestComponent
public class RemoteKeyApiActions {

    @Autowired
    private TestRestTemplate restTemplate;

    public void register(String urlId, String remoteKey) {
        RegisterRemoteKeyRequest req = new RegisterRemoteKeyRequest(remoteKey);
        restTemplate.exchange(
            "/urls/{urlId}/remote-key",
            PUT, new HttpEntity<>(req), Void.class, urlId);
    }
}
