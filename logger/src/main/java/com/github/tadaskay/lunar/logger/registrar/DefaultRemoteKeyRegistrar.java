package com.github.tadaskay.lunar.logger.registrar;

import com.github.tadaskay.lunar.logger.remotekey.RemoteKey;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKeyRegistrar;
import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlRepository;
import com.github.tadaskay.lunar.logger.url.NotFoundException;
import org.springframework.stereotype.Service;

@Service
class DefaultRemoteKeyRegistrar implements RemoteKeyRegistrar {

    private final CrawledUrlRepository repository;

    DefaultRemoteKeyRegistrar(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public RemoteKey get(String urlId) {
        CrawledUrl crawledUrl = repository.findOne(urlId);
        if (crawledUrl == null) {
            throw new NotFoundException();
        }
        return crawledUrl.getRemoteKey();
    }

    @Override
    public void register(String urlId, RemoteKey remoteKey) {
        CrawledUrl crawledUrl = repository.findOne(urlId);
        if (crawledUrl == null) {
            throw new NotFoundException();
        }
        crawledUrl.setRemoteKey(remoteKey);
        repository.save(crawledUrl);
    }
}
