package com.github.tadaskay.lunar.logger.registrar;

import com.github.tadaskay.lunar.logger.url.CrawledUrl;
import com.github.tadaskay.lunar.logger.url.CrawledUrlRepository;
import com.github.tadaskay.lunar.logger.ex.DuplicateUrlException;
import com.github.tadaskay.lunar.logger.url.UrlRegistrar;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
class DefaultUrlRegistrar implements UrlRegistrar {

    private final CrawledUrlRepository repository;

    DefaultUrlRegistrar(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CrawledUrl> list() {
        return repository.findAll();
    }

    @Override
    public List<CrawledUrl> listIncomplete() {
        return repository.findAllIncomplete();
    }

    @Override
    public CrawledUrl get(String id) {
        return repository.requireOne(id);
    }

    @Override
    public CrawledUrl register(URL url) {
        if (repository.crawledUrlExists(url)) {
            throw new DuplicateUrlException();
        }
        return repository.save(new CrawledUrl(url));
    }
}
