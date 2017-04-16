package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.TestLogApiConfiguration;
import com.github.tadaskay.lunar.logger.api.LunarUrl;
import com.github.tadaskay.lunar.logger.api.dto.CrawledUrlRepresentation;
import com.github.tadaskay.lunar.logger.api.dto.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.api.dto.RegisterCelebritiesRequest;
import com.github.tadaskay.lunar.logger.api.dto.RegisterRemoteKeyRequest;
import com.github.tadaskay.lunar.logger.util.Randoms;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@ContextConfiguration(classes = TestLogApiConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CrawledUrlResourceTest {

    @Test
    public void createsNewCrawledUrl() throws MalformedURLException {
        // given
        URL urlToCrawl = Randoms.url();
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(urlToCrawl);

        // when
        LunarUrl url = LunarUrl.create(req);
        ResponseEntity res = url.getRawResponse();

        // then
        assertThat(res.getStatusCode(), is(CREATED));
        assertNotNull(res.getHeaders().getLocation());
        assertNotNull(url.getId());
        assertThat(url.getData().getUrl(), is(urlToCrawl));
    }

    @Test
    public void doesNotAllowCrawlingSameUrlTwice() {
        // given
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(Randoms.url());

        // when
        LunarUrl.create(req);
        LunarUrl duplicate = LunarUrl.create(req);

        // then
        assertThat(duplicate.getRawResponse().getStatusCode(), is(CONFLICT));
    }

    @Test
    public void returnsNotFoundOnNonExistingUrls() {
        // when
        LunarUrl url = LunarUrl.get(Randoms.string());
        // then
        assertThat(url.getRawResponse().getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void hypermediaLinksRepresentPossibleActionsForUrl() {
        // when
        LunarUrl url = CrawledUrlFixtures.randomUrlCreated();
        // then
        assertTrue(url.getData().hasLink("register-celebrities"));
        assertTrue(url.getData().hasLink("register-remote-key"));

        // when
        url.registerCelebrities(new RegisterCelebritiesRequest());
        url = LunarUrl.get(url.getId());
        // then
        assertFalse(url.getData().hasLink("register-celebrities"));
        assertTrue(url.getData().hasLink("register-remote-key"));

        // when
        url.registerRemoteKey(new RegisterRemoteKeyRequest("foo"));
        url = LunarUrl.get(url.getId());
        // then
        assertFalse(url.getData().hasLink("register-celebrities"));
        assertFalse(url.getData().hasLink("register-remote-key"));
    }

    @Test
    public void crawlIsIncompleteUntilCelebritiesAndRemoteKeyAreRegistered() {
        // given
        LunarUrl url = CrawledUrlFixtures.randomUrlCreated();

        // when
        List<CrawledUrlRepresentation> incompleteUrls = listIncomplete();
        // then
        assertTrue(incompleteUrls.stream().anyMatch(u -> url.getId().equals(u.getId())));

        // when
        url.registerCelebrities(new RegisterCelebritiesRequest());
        incompleteUrls = listIncomplete();
        // then
        assertTrue(incompleteUrls.stream().anyMatch(u -> url.getId().equals(u.getId())));

        // when
        url.registerRemoteKey(new RegisterRemoteKeyRequest("foo"));
        incompleteUrls = listIncomplete();
        // then
        assertFalse(incompleteUrls.stream().anyMatch(u -> url.getId().equals(u.getId())));
    }

    private List<CrawledUrlRepresentation> listIncomplete() {
        return LunarUrl.listIncomplete().stream()
            .map(LunarUrl::getData)
            .collect(toList());
    }
}
