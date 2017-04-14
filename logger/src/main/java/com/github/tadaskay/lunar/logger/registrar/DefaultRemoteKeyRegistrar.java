package com.github.tadaskay.lunar.logger.registrar;

import com.github.tadaskay.lunar.logger.remotekey.RemoteKey;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKeyRegistrar;
import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlRepository;
import org.springframework.stereotype.Service;

@Service
class DefaultRemoteKeyRegistrar implements RemoteKeyRegistrar {

    private final CrawledUrlRepository repository;

    DefaultRemoteKeyRegistrar(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public RemoteKey get(String urlId) {
        return repository.requireOne(urlId).getRemoteKey();
    }

    @Override
    public void register(String urlId, RemoteKey remoteKey) {
        CrawledUrl crawledUrl = repository.requireOne(urlId);
        crawledUrl.setRemoteKey(remoteKey);
        repository.save(crawledUrl);
    }
}
