package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.FixtureConfiguration;
import com.github.tadaskay.lunar.logger.api.CrawledUrlApiResource;
import com.github.tadaskay.lunar.logger.api.CrawledUrlRepresentation;
import com.github.tadaskay.lunar.logger.api.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.api.RegisterCelebritiesRequest;
import com.github.tadaskay.lunar.logger.celebrities.CelebritiesApiActions;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKeyApiActions;
import com.github.tadaskay.lunar.logger.util.Randoms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FixtureConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CrawledUrlResourceTest {

    @Autowired
    private CrawledUrlFixtures crawledUrlFixtures;
    @Autowired
    private CelebritiesApiActions celebritiesApiActions;
    @Autowired
    private RemoteKeyApiActions remoteKeyApiActions;

    @Test
    public void createsNewCrawledUrl() throws MalformedURLException {
        // given
        URL urlToCrawl = Randoms.url();
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(urlToCrawl);

        // when
        CrawledUrlApiResource crawledUrl = CrawledUrlApiResource.create(req);
        ResponseEntity res = crawledUrl.getRawResponse();

        // then
        assertThat(res.getStatusCode(), is(CREATED));
        assertNotNull(res.getHeaders().getLocation());
        assertNotNull(crawledUrl.getId());
        assertThat(crawledUrl.getData().getUrl(), is(urlToCrawl));
    }

    @Test
    public void doesNotAllowCrawlingSameUrlTwice() {
        // given
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(Randoms.url());

        // when
        CrawledUrlApiResource.create(req);
        CrawledUrlApiResource duplicate = CrawledUrlApiResource.create(req);

        // then
        assertThat(duplicate.getRawResponse().getStatusCode(), is(CONFLICT));
    }

    @Test
    public void returnsNotFoundOnNonExistingUrls() {
        // when
        CrawledUrlApiResource crawledUrl = CrawledUrlApiResource.get(Randoms.string());
        // then
        assertThat(crawledUrl.getRawResponse().getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void crawlIsIncompleteUntilCelebritiesAndRemoteKeyAreRegistered() {
        // given
        String crawledUrlId = crawledUrlFixtures.created();

        // when
        List<CrawledUrlRepresentation> incompleteUrls = listIncomplete();
        // then
        assertTrue(incompleteUrls.stream().anyMatch(url -> url.getId().equals(crawledUrlId)));

        // when
        celebritiesApiActions.registerCelebrities(crawledUrlId, new RegisterCelebritiesRequest());
        incompleteUrls = listIncomplete();
        // then
        assertTrue(incompleteUrls.stream().anyMatch(url -> url.getId().equals(crawledUrlId)));

        // when
        remoteKeyApiActions.register(crawledUrlId, "foo");
        incompleteUrls = listIncomplete();
        // then
        assertFalse(incompleteUrls.stream().anyMatch(url -> url.getId().equals(crawledUrlId)));
    }

    private List<CrawledUrlRepresentation> listIncomplete() {
        return CrawledUrlApiResource.listIncomplete().stream()
            .map(CrawledUrlApiResource::getData)
            .collect(toList());
    }
}
