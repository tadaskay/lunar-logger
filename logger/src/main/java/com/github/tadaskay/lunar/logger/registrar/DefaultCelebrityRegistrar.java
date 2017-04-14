package com.github.tadaskay.lunar.logger.registrar;

import com.github.tadaskay.lunar.logger.celebrities.CelebritiesRegistrar;
import com.github.tadaskay.lunar.logger.celebrities.Celebrity;
import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlRepository;
import com.github.tadaskay.lunar.logger.url.NotFoundException;
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
        CrawledUrl crawledUrl = repository.findOne(urlId);
        if (crawledUrl == null) {
            throw new NotFoundException();
        }
        return crawledUrl.getCelebrities();
    }

    @Override
    public void register(String urlId, List<Celebrity> celebrities) {
        CrawledUrl url = repository.findOne(urlId);
        if (url == null) {
            throw new NotFoundException();
        }
        url.getCelebrities().addAll(celebrities);
        repository.save(url);
    }
}
