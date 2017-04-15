package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.api.CrawledUrlApiResource;
import com.github.tadaskay.lunar.logger.api.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.util.Randoms;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CrawledUrlFixtures {

    public String created() {
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(Randoms.url());
        CrawledUrlApiResource crawledUrl = CrawledUrlApiResource.create(req);
        return crawledUrl.getId();
    }
}
