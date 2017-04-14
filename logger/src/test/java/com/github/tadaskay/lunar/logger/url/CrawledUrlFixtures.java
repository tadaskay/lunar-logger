package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.util.Randoms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

@TestComponent
public class CrawledUrlFixtures {

    @Autowired
    private TestRestTemplate restTemplate;

    public String created() {
        URL urlToCrawl = Randoms.url();
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest();
        req.setUrl(urlToCrawl);

        ResponseEntity<Void> res = restTemplate.postForEntity("/urls", req, Void.class);
        URI createdUrlLocation = res.getHeaders().getLocation();

        ResponseEntity<CrawledUrl> createdUrlRes = restTemplate.getForEntity(createdUrlLocation, CrawledUrl.class);
        String createdUrlId = createdUrlRes.getBody().getId();
        assertNotNull(createdUrlId);
        return createdUrlId;
    }

}
