package com.github.tadaskay.lunar.logger.url;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/urls")
class CrawledUrlResource {

    private final CrawledUrlRepository repository;

    public CrawledUrlResource(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CrawledUrl> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<CrawledUrl> create(@Valid @RequestBody CreateCrawledUrlRequest request,
                                             UriComponentsBuilder uriBuilder) {
        if (repository.crawledUrlExists(request.getUrl())) {
            return new ResponseEntity<>(CONFLICT);
        }

        CrawledUrl crawledUrl = new CrawledUrl(request.getUrl());
        crawledUrl = repository.save(crawledUrl);

        URI location = uriBuilder.path("/urls/{id}").buildAndExpand(crawledUrl.getId()).toUri();
        return created(location).body(crawledUrl);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrawledUrl> get(@PathVariable("id") String id) {
        CrawledUrl crawledUrl = repository.findOne(id);
        if (crawledUrl == null) {
            return notFound().build();
        }
        return ok(crawledUrl);
    }
}
