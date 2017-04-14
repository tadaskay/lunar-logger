package com.github.tadaskay.lunar.logger.url;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.net.URL;
import java.util.Optional;

public interface CrawledUrlRepository extends MongoRepository<CrawledUrl, String> {

    @Query(value = "{ 'url': ?0 }", exists = true)
    boolean crawledUrlExists(URL url);

    default CrawledUrl requireOne(String id) throws NotFoundException {
        return Optional.ofNullable(findOne(id))
                .orElseThrow(NotFoundException::new);
    }
}
