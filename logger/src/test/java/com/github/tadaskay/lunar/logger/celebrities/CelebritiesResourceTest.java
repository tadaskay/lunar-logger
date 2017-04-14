package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.FixtureConfiguration;
import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlApiActions;
import com.github.tadaskay.lunar.logger.url.CrawledUrlFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FixtureConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CelebritiesResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CrawledUrlFixtures crawledUrlFixtures;
    @Autowired
    private CrawledUrlApiActions crawledUrlApiActions;
    @Autowired
    private CelebritiesFixtures celebritiesFixtures;
    @Autowired
    private CelebritiesApiActions celebritiesApiActions;

    @Test
    public void registersCelebrities() {
        // given
        String urlId = crawledUrlFixtures.created();
        RegisterCelebritiesRequest req = new RegisterCelebritiesRequest()
            .withEntry(celebritiesFixtures.registrableEntry())
            .withEntry(celebritiesFixtures.registrableEntry());

        // when
        ResponseEntity<Void> res = celebritiesApiActions.registerCelebrities(urlId, req);
        // then
        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void marksCelebritiesAsReceivedUponRegistration() {
        // given
        String urlId = crawledUrlFixtures.created();

        // when
        CrawledUrl crawledUrl = crawledUrlApiActions.get(urlId);
        // then
        assertFalse(crawledUrl.isCelebritiesReceived());

        // when
        RegisterCelebritiesRequest reqWithNoEntries = new RegisterCelebritiesRequest();
        celebritiesApiActions.registerCelebrities(urlId, reqWithNoEntries);
        crawledUrl = crawledUrlApiActions.get(urlId);
        // then
        assertTrue(crawledUrl.isCelebritiesReceived());
    }
}
