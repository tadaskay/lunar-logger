package com.github.tadaskay.lunar.logger.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@TestComponent
public class CrawledUrlApiActions {

    @Autowired
    private TestRestTemplate restTemplate;

    public CrawledUrl get(String id) {
        ResponseEntity<CrawledUrl> res = restTemplate.getForEntity("/urls/{urlId}", CrawledUrl.class, id);
        assert res.getStatusCode().is2xxSuccessful();
        return res.getBody();
    }
}
