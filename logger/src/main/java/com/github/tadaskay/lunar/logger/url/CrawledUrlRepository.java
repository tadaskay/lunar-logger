package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.ex.NotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface CrawledUrlRepository extends MongoRepository<CrawledUrl, String> {

    @Query(value = "{ 'url': ?0 }", exists = true)
    boolean crawledUrlExists(URL url);

    @Query("{ '$or': [{ 'celebritiesReceived': false }, { 'remoteKey': { $eq : null } }] }")
    List<CrawledUrl> findAllIncomplete();

    default CrawledUrl requireOne(String id) throws NotFoundException {
        return Optional.ofNullable(findOne(id))
            .orElseThrow(NotFoundException::new);
    }
}
