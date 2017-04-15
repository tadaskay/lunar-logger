package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.api.LunarUrl;
import com.github.tadaskay.lunar.logger.api.dto.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.util.Randoms;

public class CrawledUrlFixtures {

    public static LunarUrl randomUrlCreated() {
        CreateCrawledUrlRequest req = new CreateCrawledUrlRequest(Randoms.url());
        return LunarUrl.create(req);
    }
}
