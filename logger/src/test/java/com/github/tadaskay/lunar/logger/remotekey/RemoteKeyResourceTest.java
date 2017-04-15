package com.github.tadaskay.lunar.logger.remotekey;

import com.github.tadaskay.lunar.logger.TestLogApiConfiguration;
import com.github.tadaskay.lunar.logger.api.CrawledUrlApiResource;
import com.github.tadaskay.lunar.logger.api.RegisterRemoteKeyRequest;
import com.github.tadaskay.lunar.logger.url.CrawledUrlFixtures;
import com.github.tadaskay.lunar.logger.util.Randoms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestLogApiConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RemoteKeyResourceTest {

    @Test
    public void registersRemoteKey() {
        // given
        CrawledUrlApiResource crawledUrl = CrawledUrlFixtures.randomUrlCreated();
        String remoteKeyValue = Randoms.string();
        RegisterRemoteKeyRequest req = new RegisterRemoteKeyRequest(remoteKeyValue);

        // when
        crawledUrl.registerRemoteKey(req);
        CrawledUrlApiResource updated = CrawledUrlApiResource.get(crawledUrl.getId());

        // then
        assertThat(updated.getData().getRemoteKey().getValue(), is(remoteKeyValue));
    }
}
