package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.FixtureConfiguration;
import com.github.tadaskay.lunar.logger.url.CrawledUrlFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FixtureConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CelebritiesResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CrawledUrlFixtures crawledUrlFixtures;
    @Autowired
    private CelebritiesFixtures celebritiesFixtures;

    @Test
    public void registersCelebrities() {
        // given
        String crawledUrlId = crawledUrlFixtures.created();
        RegisterCelebritiesRequest req = new RegisterCelebritiesRequest();
        req.addEntry(celebritiesFixtures.registrableEntry());
        req.addEntry(celebritiesFixtures.registrableEntry());

        // when
        ResponseEntity<Void> res = restTemplate.exchange(
            "/urls/{urlId}/celebrities",
            PUT, new HttpEntity<>(req), Void.class, crawledUrlId);
        // then
        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

}
