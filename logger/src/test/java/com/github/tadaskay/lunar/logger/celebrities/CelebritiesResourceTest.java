package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.TestLogApiConfiguration;
import com.github.tadaskay.lunar.logger.api.LunarUrl;
import com.github.tadaskay.lunar.logger.api.dto.CelebrityRepresentation;
import com.github.tadaskay.lunar.logger.api.dto.RegisterCelebritiesRequest;
import com.github.tadaskay.lunar.logger.url.CrawledUrlFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestLogApiConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CelebritiesResourceTest {

    @Test
    public void registersCelebrities() {
        // given
        LunarUrl url = CrawledUrlFixtures.randomUrlCreated();
        RegisterCelebritiesRequest.Entry celebrity1 = CelebritiesFixtures.registrableEntry();
        RegisterCelebritiesRequest.Entry celebrity2 = CelebritiesFixtures.registrableEntry();
        RegisterCelebritiesRequest req = new RegisterCelebritiesRequest().withEntry(celebrity1).withEntry(celebrity2);

        // when
        url.registerCelebrities(req);
        LunarUrl updatedUrl = LunarUrl.get(url.getId());
        // then
        assertTrue(updatedUrl.getData().getCelebrities().stream()
            .anyMatch(c -> equalsToEntry(c, celebrity1)));
        assertTrue(updatedUrl.getData().getCelebrities().stream()
            .anyMatch(c -> equalsToEntry(c, celebrity2)));
    }

    private static boolean equalsToEntry(CelebrityRepresentation c, RegisterCelebritiesRequest.Entry entry) {
        return c.getSceneName().equals(entry.getSceneName())
            && c.getFirstName().equals(entry.getFirstName())
            && c.getLastName().equals(entry.getLastName());
    }

    @Test
    public void marksCelebritiesAsReceivedUponRegistration() {
        // when
        LunarUrl url = CrawledUrlFixtures.randomUrlCreated();
        // then
        assertFalse(url.getData().isCelebritiesReceived());

        // when
        url.registerCelebrities(new RegisterCelebritiesRequest());
        LunarUrl updated = LunarUrl.get(url.getId());
        // then
        assertTrue(updated.getData().isCelebritiesReceived());
    }

    @Test
    public void cannotReRegisterCelebrities() {
        // given
        LunarUrl url = CrawledUrlFixtures.randomUrlCreated();

        // when
        ResponseEntity<Void> res = url.registerCelebrities(new RegisterCelebritiesRequest());
        // then
        assertTrue(res.getStatusCode().is2xxSuccessful());

        // when
        res = url.registerCelebrities(new RegisterCelebritiesRequest());
        // then
        assertThat(res.getStatusCode(), is(UNPROCESSABLE_ENTITY));
    }
}
