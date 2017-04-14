package com.github.tadaskay.lunar.logger.remotekey;

import com.github.tadaskay.lunar.logger.FixtureConfiguration;
import com.github.tadaskay.lunar.logger.url.CrawledUrlFixtures;
import com.github.tadaskay.lunar.logger.util.Randoms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FixtureConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RemoteKeyResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CrawledUrlFixtures crawledUrlFixtures;

    @Test
    public void registersRemoteKey() {
        // given
        String urlId = crawledUrlFixtures.created();
        String remoteKeyValue = Randoms.string();
        RegisterRemoteKeyRequest req = new RegisterRemoteKeyRequest(remoteKeyValue);

        // when
        ResponseEntity<Void> res = restTemplate.exchange(
            "/urls/{urlId}/remote-key",
            PUT, new HttpEntity<>(req), Void.class, urlId);
        // then
        assertTrue(res.getStatusCode().is2xxSuccessful());

        // when
        ResponseEntity<RemoteKey> registeredKeyRes = restTemplate.getForEntity("/urls/{urlId}/remote-key", RemoteKey.class, urlId);
        // then
        assertThat(registeredKeyRes.getBody().getValue(), is(remoteKeyValue));
    }
}
