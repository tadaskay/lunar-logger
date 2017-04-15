package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.api.CrawledUrlApiResource;
import com.github.tadaskay.lunar.logger.api.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.util.Randoms;

public class CrawledUrlFixtures {

    public static CrawledUrlApiResource randomUrlCreated() {
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(Randoms.url());
        return CrawledUrlApiResource.create(req);
    }
}
