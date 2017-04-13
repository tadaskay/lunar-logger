package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.util.Randoms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CrawledUrlResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createsNewCrawledUrl() throws MalformedURLException {
        // given
        URL urlToCrawl = Randoms.url();
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest();
        req.setUrl(urlToCrawl);

        // when
        ResponseEntity<Void> res = restTemplate.postForEntity("/urls", req, Void.class);
        // then
        assertThat(res.getStatusCode(), is(CREATED));
        URI location = res.getHeaders().getLocation();
        assertNotNull(location);

        // when
        ResponseEntity<CrawledUrl> created = restTemplate.getForEntity(location, CrawledUrl.class);
        // then
        assertThat(created.getBody().getUrl(), is(urlToCrawl));
    }

    @Test
    public void doesNotAllowCrawlingSameUrlTwice() {
        // given
        URL urlToCrawl = Randoms.url();
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest();
        req.setUrl(urlToCrawl);

        // when
        restTemplate.postForEntity("/urls", req, Void.class);
        ResponseEntity<Void> res = restTemplate.postForEntity("/urls", req, Void.class);

        // then
        assertThat(res.getStatusCode(), is(CONFLICT));
    }

    @Test
    public void returnsNotFoundOnNonExistingUrls() {
        // when
        ResponseEntity<CrawledUrl> res = restTemplate.getForEntity("/urls/{id}", CrawledUrl.class, Randoms.string());
        // then
        assertThat(res.getStatusCode(), is(NOT_FOUND));
    }

}
