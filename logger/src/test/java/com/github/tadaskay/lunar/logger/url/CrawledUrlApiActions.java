package com.github.tadaskay.lunar.logger.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@TestComponent
public class CrawledUrlApiActions {

    @Autowired
    private TestRestTemplate restTemplate;

    public CrawledUrl get(String id) {
        ResponseEntity<CrawledUrl> res = restTemplate.getForEntity("/urls/{urlId}", CrawledUrl.class, id);
        assert res.getStatusCode().is2xxSuccessful();
        return res.getBody();
    }

    public List<CrawledUrl> list(boolean incomplete) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/urls");
        if (incomplete) {
            uriBuilder.queryParam("incomplete", "true");
        }
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<List<CrawledUrl>> res = restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<CrawledUrl>>() {
        });
        assert res.getStatusCode().is2xxSuccessful();
        return res.getBody();
    }
}
