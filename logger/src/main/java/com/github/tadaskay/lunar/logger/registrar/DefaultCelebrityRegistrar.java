package com.github.tadaskay.lunar.logger.registrar;

import com.github.tadaskay.lunar.logger.celebrities.CelebritiesRegistrar;
import com.github.tadaskay.lunar.logger.celebrities.Celebrity;
import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DefaultCelebrityRegistrar implements CelebritiesRegistrar {

    private final CrawledUrlRepository repository;

    DefaultCelebrityRegistrar(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Celebrity> list(String urlId) {
        return repository.requireOne(urlId).getCelebrities();
    }

    @Override
    public void register(String urlId, List<Celebrity> celebrities) {
        CrawledUrl url = repository.requireOne(urlId);
        url.setCelebritiesReceived(true);
        url.setCelebrities(celebrities);
        repository.save(url);
    }
}
