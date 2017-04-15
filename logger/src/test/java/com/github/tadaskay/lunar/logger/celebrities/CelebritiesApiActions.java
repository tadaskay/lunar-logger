package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.api.RegisterCelebritiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpMethod.PUT;

@TestComponent
public class CelebritiesApiActions {

    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<Void> registerCelebrities(String urlId, RegisterCelebritiesRequest req) {
        return restTemplate.exchange(
            "/urls/{id}/celebrities",
            PUT, new HttpEntity<>(req), Void.class, urlId);
    }
}
